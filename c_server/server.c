//
// Created by Ondřej Váně on 21.11.18.
//

#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <unistd.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <time.h>
#include <sys/ioctl.h>
#include <string.h>
#include <pthread.h>
#include <ctype.h>
#include <arpa/inet.h>
#include <net/if.h>
#include "server.h"
#include "global_variables.h"
#include "game.h"
#include "server_listener.h"
#include "time_checker.h"
#include "game_controller.h"
#include "loger.h"
#include "main.h"


/**
 * Funkce resp. vlákno, které kontroluje přípojení nového klienta do lobby.
 * Po přijetí příkazu find;nick; čeká na soupeře. Pokud je soupeř nalezen
 * Spustí se nové vlákno pro hru, které obsluhuje danou hru.
 * @return
 */
int start_server(){
    game *game_struct;
    game **temp_reconnect;
    int a2read;
    int wrong_message_counter[1024] = {0};
    char *cbuf;
    char *tempNick;
    fd_set tests;
    char playerName1[32];
    char playerName2[32];
    int server_socket;
    int client_socket, fd;
    struct sockaddr_in my_addr, peer_addr;
    pthread_t server_listener_thread;
    pthread_t time_checker_thread;
    pthread_t game_thread;
    struct timeval tv = {1, 0};

    //inicializace logeru
    log_server_init();

    printf("Server is starting...\n");

    server_socket = socket(AF_INET, SOCK_STREAM, 0);

    int param = 1;


    if (setsockopt(server_socket, SOL_SOCKET, SO_REUSEADDR, (const char *) &param, sizeof(int)) == -1)
    {
        printf("setsockopt ERR\n");
        exit(1);
    }


    memset(&my_addr, 0, sizeof(struct sockaddr_in));

    my_addr.sin_family = AF_INET;
    my_addr.sin_port = htons(server_port);
    if(ip_set_flag == 0){
        my_addr.sin_addr.s_addr = INADDR_ANY;
    } else{
        my_addr.sin_addr.s_addr = inet_addr(ip_address);
        local_ip = ip_address;
    }




    if (bind(server_socket, (struct sockaddr *) &my_addr, sizeof(struct sockaddr_in)) != 0)
    {
        printf("Bind - ERROR\n");
        exit(1);
    }

    if (listen(server_socket, 5) != 0){
        printf("Listen - ER\n");
        exit(1);
    }

    //zjištění lokální ip adresy, nefunguje na linux!!
    /*
    struct ifreq ifr;
    ifr.ifr_addr.sa_family = AF_INET;
    strncpy(ifr.ifr_name, "en0", IFNAMSIZ-1);
    ioctl(server_socket, SIOCGIFADDR, &ifr);
    local_ip = inet_ntoa(((struct sockaddr_in *)&ifr.ifr_addr)->sin_addr);
    */
    if(ip_set_flag == 0){
        local_ip = "147.228.67.108";
    }

    //inicializace logeru
    log_server_init();

    printf("Server successfully started\n");
    printf("%s", cli_delim);
    printf("Server is running on %s:%d\n", local_ip, server_port);
    printf("%s", cli_delim);

    // spustí vlákno pro naslouchání prikazu pro server
    pthread_create(&server_listener_thread, NULL, &server_listener, NULL);

    // vyprazdnime sadu deskriptoru a vlozime server socket
    FD_ZERO(&client_socks);
    FD_SET(server_socket, &client_socks);

    while (1)
    {
        // zkopirujeme si fd_set do noveho, stary by byl znicen (select ho modifikuje)
        tests = client_socks;

        tv.tv_sec = 1;

        // sada deskriptoru je po kazdem volani select prepsana sadou deskriptoru kde se neco delo
        int return_value = select(FD_SETSIZE, &tests, (fd_set*)NULL, (fd_set*)NULL, &tv);
        if ( return_value < 0)
        {
            printf("Select ERR\n");
            exit(1);
        }

        // vynechavame stdin, stdout, stderr
        for (fd = 3; fd < FD_SETSIZE; fd++)
        {
            // je dany socket v sade fd ze kterych lze cist ?
            if (FD_ISSET(fd, &tests))
            {
                // je to server socket? prijmeme nove spojeni
                if (fd == server_socket)
                {
                    socklen_t peer_addr_len = sizeof(peer_addr);
                    client_socket = accept(server_socket, (struct sockaddr *) &peer_addr, &peer_addr_len);
                    FD_SET(client_socket, &client_socks);
                    number_of_clients++;
                    printf("SERVER: Pripojen novy klient se socketem %d\n", client_socket);
                    log_server_add("SERVER: Připojen novy klient.\n");
                }
                else // je to klientsky socket? prijmem data
                {
                    // pocet bajtu co je pripraveno ke cteni
                    ioctl(fd, FIONREAD, &a2read);
                    // mame co cist
                    if (a2read > 0)
                    {
                        cbuf = (char*)calloc(a2read*sizeof(char), 1);
                        //pokud je správa delší jak 40 znaků(nevalidní), tak zahazuju
                        if(a2read>40){
                            send(fd, "fail;message_ignored;", 21, 0);
                            printf("SERVER: Zpráva od %d ignorována!\n", fd);
                            recv(fd, cbuf, a2read, 0);
                            wrong_message_counter[fd]++;
                            //pokud client zaslal více špatných zpráv je odpojen (pokus a narušení serveru)
                            if(wrong_message_counter[fd]>=count_of_wrong_message){
                                printf("SERVER: Klient %d odpojen!(Pokus o narušení stability serveru!)\n", fd);
                                log_server_add("SERVER: Klient odpojen!(Pokus o narušení stability serveru!).\n");
                                send(fd, "fail;disconnected;", 18, 0);
                                close(fd);
                                FD_CLR(fd, &client_socks);
                                wrong_message_counter[fd]=0;
                            }
                            free(cbuf);
                            continue;
                        }

                        if(wrong_message_counter[fd]>=count_of_wrong_message){
                            printf("SERVER: Klient %d odpojen!(Pokus o narušení stability serveru!)\n", fd);
                            log_server_add("SERVER: Klient odpojen!(Pokus o narušení stability serveru!).\n");
                            send(fd, "fail;disconnected;", 18, 0);
                            close(fd);
                            FD_CLR(fd, &client_socks);
                            wrong_message_counter[fd]=0;
                            free(cbuf);
                            continue;
                        }

                        recv(fd, cbuf, a2read, 0);
                        printf("SERVER: Přijato serverem od %d: %s\n", fd, cbuf);
                        log_counter_recieve(a2read);

                        char *command;
                        //zjištění zasaného příkazu od klienta
                        command = strtok(cbuf, ";");
                        //příkaz find pro hledání protihráče
                        if(strcmp("find", command) == 0){

                            tempNick = strtok(NULL, ";");


                            if(client_socket_buffer[0] == 0 && client_socket_buffer[1] == 0)
                            {

                                client_socket_buffer[0] = fd;
                                strncpy(playerName1, tempNick, 31);
                                playerName1[31]='\0';
                                printf("SERVER: Hledám protihráče pro %s se socketem %d (Cekam %d s)\n", playerName1, client_socket_buffer[0], time_to_wait);
                                pthread_create(&time_checker_thread, NULL, &time_checker, NULL);
                                number_of_clients++;
                            }
                            else if(client_socket_buffer[0] != 0 && client_socket_buffer[1] == 0)
                            {
                                client_socket_buffer[1] = fd;
                                strncpy(playerName2, tempNick, 31);
                                playerName2[31]= '\0';
                                number_of_clients++;
                            }

                            if(client_socket_buffer[0] == client_socket_buffer[1]){
                                printf("SERVER: Nelze vytvořit hru sám ze sebou!\n");
                            }else if (client_socket_buffer[0] !=0 && client_socket_buffer[1] != 0){
			

			
				                if(count_of_games < max_count_of_game){
                                    game_struct = add_game(&allgames, client_socket_buffer[0], client_socket_buffer[1], playerName1, playerName2);
                                    if(pthread_create(&game_thread, NULL, &game_controller, (void *)game_struct) != 0){
                                        printf("SERVER: Nemohl jsem vytvořit hru\n");
                                    }
                                    else{
                                        printf("SERVER: Vytvořena nová hra %d: %s vs. %s \n", game_struct->game_id, game_struct->p2_nick, game_struct->p1_nick);
                                        log_server_add("SERVER: Vytvořená nová hra.\n");
                                    }
                                    pthread_join(time_checker_thread, NULL);

                                    FD_CLR(client_socket_buffer[0], &client_socks);
                                    FD_CLR(client_socket_buffer[1], &client_socks);
                                    client_socket_buffer[0] = 0;
                                    client_socket_buffer[1] = 0;
                                    count_of_games++;
				                } else{
				                    send(client_socket_buffer[0], "opponent;full;", 14, 0);
                                    send(client_socket_buffer[1], "opponent;full;", 14, 0);
                                    client_socket_buffer[0] = 0;
                                    client_socket_buffer[1] = 0;
				                }

                            }
                            //Přijatý příkaz od klienta k odpojení od serveru;
                        } else if(strcmp("exit", command) == 0){
                            close(fd);
                            FD_CLR(fd, &client_socks);
                            number_of_clients--;
                            printf("SERVER: Klient se socketem %d se odpojil prikazem exit\n", fd);
                        } else if(strcmp("reconnect", command) == 0){
                            tempNick = strtok(NULL, ";");
                            temp_reconnect = list_search_nick(&allgames, tempNick);

                            if ( temp_reconnect != NULL){
                                if(strcmp((*temp_reconnect)->p1_nick, tempNick) == 0){
                                    (*temp_reconnect)->p1_reconnect = fd;
                                    send(fd, "reconnect;success;", 18, 0);

                                } else{
                                    (*temp_reconnect)->p2_reconnect = fd;
                                    send(fd, "reconnect;success;", 18, 0);
                                }
                                if((*temp_reconnect)->your_turn == 0){
                                    (*temp_reconnect)->your_turn = fd;
                                }
                                FD_CLR(fd, &client_socks);
                            } else{
                                send(fd, "reconnect;fail;", 15, 0);
                            }

                        } else if(strcmp("sync", command) == 0){
                            send(fd, "fail;sync;", 10, 0);

                        } else if((strcmp("fire", command) == 0)){
                            //nic nědělat, je to ještě zbytek zprávy ze hry
                        } else{
                            //špatný příkaz
                            wrong_message_counter[fd]++;
                            send(fd, "fail;message;", 13, 0);
                        }
                        free(cbuf);

                    }
                    else // na socketu se stalo neco spatneho
                    {
                        if (client_socket_buffer[0] == fd){
                            client_socket_buffer[0] = 0;

                        }
                        //number_of_clients--;
                        close(fd);
                        FD_CLR(fd, &client_socks);
                        printf("SERVER: Klient se odpojil a byl odebrán ze sady socketů.\n");
                        log_server_add("SERVER: Klient se odpojil a byl odebrán ze sady socketů.\n");
                    }
                }
            }
        }

    }
    return 0;

}
