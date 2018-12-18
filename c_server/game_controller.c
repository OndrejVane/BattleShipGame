//
// Created by Ondřej Váně on 21.11.18.
//

#include "game.h"
#include "global_variables.h"
#include "loger.h"
#include "time_checker.h"
#include "reconnect_checker.h"
#include "game_controller.h"
#include "ping.h"
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <unistd.h>
#include <pthread.h>
#include <sys/ioctl.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

/**
 * Vlákno pro ovládání jednotlivé hry. Select naslouchá oboum klientům a zpracovává
 * příchozí zprávy. Na základě přijaté zprávy odesílá odpovídající výsledek.
 * @param args
 * @return
 */
void *game_controller(void *args) {

    game *hra;
    hra = (game *) args;
    fd_set player_socks, tests;
    int wrong_message_counter[1024] = {0};
    char *buffer;
    char *temp, *temp2;
    int a2read, fd;
    char *command;
    int fire_result;
    pthread_t reconnect_thread;
    pthread_t ping_thread;
    struct timeval tv = {1, 0};

    // vyprazdnime sadu deskriptoru a vlozime server socket
    FD_ZERO(&player_socks);
    FD_SET(hra->p1_socket, &player_socks);
    FD_SET(hra->p2_socket, &player_socks);
    hra->player_socks = &player_socks;

    //start vlákna pro odesílání pingu oboum klientům
    pthread_create(&ping_thread, NULL, &ping, (void *)hra);

    while(hra->is_running) {
        // zkopirujeme si fd_set do noveho, stary by byl znicen (select ho modifikuje)
        tests = player_socks;

        // opětovné nastavení tv
        tv.tv_sec = 1;

        int return_value_game = select(FD_SETSIZE, &tests, (fd_set *) NULL, (fd_set *) NULL, &tv);
        if (return_value_game < 0) {
            printf("Select ERR\n");
            pthread_exit((void *) 0);

        }


        // vynechavame stdin, stdout, stderr
        for (fd = 3; fd < FD_SETSIZE; fd++) {
            if(hra->p1_socket == fd && hra->p1_is_reachable == -1){
                hra->p1_is_reachable = 0;
                goto START_RECONNECT;
            }

            if(hra->p2_socket == fd && hra->p2_is_reachable == -1){
                hra->p2_is_reachable = 0;
                goto START_RECONNECT;
            }
            // je dany socket v sade fd ze kterych lze cist ?
            if (FD_ISSET(fd, &tests)) {
                // pocet bajtu co je pripraveno ke cteni
                ioctl(fd, FIONREAD, &a2read);
                // mame co cist
                if (a2read > 1) {

                    //alokování paměti pro buffer a pomocné proměnné
                    buffer = calloc(sizeof(char)*a2read, 1);
                    temp = calloc(sizeof(char)*a2read, 1);
                    temp2 = calloc(sizeof(char)*a2read, 1);
                    recv(fd, buffer, a2read, 0);

                    printf("HRA č.%d: Přijato od %d: %s\n", hra->game_id, fd, buffer);
                    log_counter_recieve(a2read);

                    if(wrong_message_counter[fd]>=count_of_wrong_message){
                        printf("HRA č.%d: Klient byl odpoje!(Pokus o narušení stability serveru!)\n", hra->game_id);
                        send(fd, "fail;disconnected;", 18, 0);
                        close(fd);
                        FD_CLR(fd, &player_socks);
                        wrong_message_counter[fd]=0;
                        free(buffer);
                        free(temp);
                        free(temp2);
                        goto START_RECONNECT;
                    }

                    strcpy(temp, buffer);
                    strcpy(temp2, buffer);

                    //funkce, která zkontroluje, zda je zpráva validní
                    if (is_valid_message(temp2) == 1) {
                        command = strtok(buffer, ";");

                        // Příjem zprávy pro nastavení gridu
                        if (strcmp("grid", command) == 0) {
                            //nastavení gridu pro 1.hráče
                            if (fd == hra->p1_socket && hra->p1_set == 0) {
                                if (set_ships(temp, hra, fd) == 1) {
                                    printf("HRA č.%d: Lode pro 1. hráče nastaveny!\n", hra->game_id);
                                    //send(fd, "grid;success;", 13, 0);
                                    hra->p1_set = 1;
                                    if (hra->p1_set != 0 && hra->p2_set != 0) {
                                        printf("HRA č.%d: Hra může začít. Začíná 2. hráč.\n", hra->game_id);
                                        send(hra->p1_socket, "start;0;", 8, 0);
                                        log_counter(8);
                                        send(hra->p2_socket, "start;1;", 8, 0);
                                        log_counter(8);
                                        hra->your_turn = hra->p2_socket;
                                    }
                                } else {
                                    send(fd, "fail;message;", 13, 0);
                                    log_counter(13);
                                }

                            } else {
                                if (fd == hra->p1_socket){
                                    send(fd, "fail;grid;", 10, 0);
                                    log_counter(10);
                                }

                            }
                            //nastavení gridu pro 2.hráče
                            if (fd == hra->p2_socket && hra->p2_set == 0) {
                                if ( set_ships(temp, hra, fd) == 1){
                                    printf("HRA č.%d: Lode pro 2. hráče nastaveny!\n", hra->game_id);
                                    //send(fd, "grid;success;", 13, 0);
                                    hra->p2_set = 1;
                                    if (hra->p1_set != 0 && hra->p2_set != 0) {
                                        printf("HRA č.%d: Hra může začít. Začíná 1. hráč.\n", hra->game_id);
                                        send(hra->p1_socket, "start;1;", 8, 0);
                                        log_counter(8);
                                        send(hra->p2_socket, "start;0;", 8, 0);
                                        log_counter(8);
                                        hra->your_turn = hra->p1_socket;
                                    }
                                } else{
                                    send(fd, "fail;message;", 13, 0);
                                    log_counter(13);
                                }

                            } else {
                                if (fd == hra->p2_socket){
                                    send(fd, "fail;grid;", 10, 0);
                                    log_counter(10);
                                }
                            }
                        //Příjem zprávy pro střelbu
                        } else if (strcmp("fire", command) == 0) {
                            if (hra->p1_set != 0 && hra->p2_set != 0) {

                                //stříli hrac p1
                                //Kontrola od koho vystřel přišel a jestli je na řade
                                if (fd == hra->p1_socket && fd == hra->your_turn) {
                                    fire_result = fire(temp, hra, fd);

                                    if (fire_result == 1) {

                                        send(hra->p1_socket, "response;1;", 11, 0);
                                        log_counter(11);
                                        hra->p2_ships--;              //snížení počtu lodí při zásahu

                                        //kontrola. zda už hráč neprohrál, pokud ano poslat zprávu result!
                                        if (hra->p2_ships == 0) {
                                            wait_for(1);
                                            send(hra->p1_socket, "result;win;", 11, 0);
                                            log_counter(11);
                                            send(hra->p2_socket, "result;lose;", 12, 0);
                                            log_counter(12);
                                            printf("HRA č.%d: Hráč %s vyhrál! Hra byla ukončena.\n", hra->game_id,
                                                   hra->p1_nick);
                                            //odstranění socketu z vlákny hry
                                            FD_CLR(hra->p1_socket, &player_socks);
                                            FD_CLR(hra->p2_socket, &player_socks);

                                            //přidání socketů do hlavního selectu
                                            FD_SET(hra->p1_socket, &client_socks);
                                            FD_SET(hra->p2_socket, &client_socks);

                                            hra->is_running = 0;


                                            //Odebrání hry ze seznamu her
                                            sleep(2);
                                            list_remove(list_search(&allgames, hra->game_id));

                                            //snížení počtu probíhajících her
                                            count_of_games--;

                                            //ukončení vlákna pro hru!!
                                            pthread_exit(0);
                                        }

                                        hra->your_turn = hra->p2_socket;            //Nastavení kola pro protihráče
                                    } else if (fire_result == 0) {
                                        send(hra->p1_socket, "response;0;", 11, 0);
                                        log_counter(11);
                                        hra->your_turn = hra->p2_socket;
                                    } else {
                                        send(hra->p1_socket, "response;-1;", 12, 0);
                                        log_counter(12);
                                    }


                                } else if (fd == hra->p1_socket) {
                                    printf("HRA č.%d: Na tahu je protihráč!\n", hra->game_id);
                                    send(fd, "fail;turn;", 10, 0);
                                    log_counter(10);
                                }

                                //stříli hrac p2
                                if (fd == hra->p2_socket && fd == hra->your_turn) {
                                    fire_result = fire(temp, hra, fd);
                                    if (fire_result == 1)    //zasah lode
                                    {
                                        //zkontrolovat počet lodí..pokud se rovná po výstřelu nule, tak poslat zprávu result;win/lose!!
                                        send(hra->p2_socket, "response;1;", 11, 0);
                                        log_counter(11);

                                        hra->p1_ships--;          //snížení počtu lodí protihráče

                                        //kontrola. zda už hráč neprohrál, pokud ano poslat zprávu result!
                                        if (hra->p1_ships == 0) {
                                            wait_for(1);
                                            send(hra->p2_socket, "result;win;", 11, 0);
                                            log_counter(11);
                                            send(hra->p1_socket, "result;lose;", 12, 0);
                                            log_counter(12);
                                            printf("HRA č.%d: Hráč %s vyhrál! Hra byla ukončena.\n", hra->game_id, hra->p2_nick);
                                            //odstranění socketu z vlákny hry
                                            FD_CLR(hra->p1_socket, &player_socks);
                                            FD_CLR(hra->p2_socket, &player_socks);

                                            //přidání socketů do hlavního selectu
                                            FD_SET(hra->p1_socket, &client_socks);
                                            FD_SET(hra->p2_socket, &client_socks);

                                            hra->is_running = 0;

                                            //Odebrání hry ze seznamu her
                                            sleep(2);
                                            list_remove(list_search(&allgames, hra->game_id));

                                            //snížení počtu probíhajících her
                                            count_of_games--;

                                            //ukončení vlákna pro hru!!
                                            pthread_exit(0);
                                        }

                                        hra->your_turn = hra->p1_socket;
                                    } else if (fire_result == 0)      //vedle
                                    {
                                        send(hra->p2_socket, "response;0;", 11, 0);
                                        log_counter(11);
                                        hra->your_turn = hra->p1_socket;
                                    } else {                         //tady už jsi střílel
                                        send(hra->p2_socket, "response;-1;", 12, 0);
                                        log_counter(12);
                                    }


                                } else if (fd == hra->p2_socket) {
                                    printf("HRA č.%d: Na tahu je protihráč!\n", hra->game_id);
                                    send(fd, "fail;turn;", 10, 0);
                                    log_counter(10);
                                }


                            } else {
                                //pokud ještě nemají oba nastavený grid
                                printf("HRA č.%d: Ještě střílet nemůžeš!!\n", hra->game_id);
                                send(fd, "fail;grid;", 10, 0);
                                log_counter(10);
                            }
                        } else if (strcmp("sync", command) == 0) {
                            if(fd == hra->p1_socket && hra->p1_set == 0){
                                send(fd, "sync;not_set;", 13, 0);
                                log_counter(13);
                            }else if (fd == hra->p1_socket && hra->p1_set == 1){
                                send(fd, make_sync_message(hra, fd), 170, 0);
                                log_counter(170);
                            }

                            if(fd == hra->p2_socket && hra->p2_set == 0){
                                send(fd, "sync;not_set;", 13, 0);
                                log_counter(13);
                            }else if (fd == hra->p2_socket && hra->p2_set == 1){
                                send(fd, make_sync_message(hra, fd), 170, 0);
                                log_counter(170);
                            }
                        } else if(strcmp("ping", command) == 0){
                            if(fd == hra->p1_socket){
                                hra->p1_is_reachable = 1;
                            }

                            if(fd == hra->p2_socket){
                                hra->p2_is_reachable = 1;
                            }
                            printf("HRA č.%d: přijat ping od %d\n", hra->game_id, fd);
                        }
                    } else {
                        send(fd, "fail;message;", 13, 0);
                        wrong_message_counter[fd]++;
                        log_counter(13);
                    }


                    //uvolnění paměti po bufferu
                    free(buffer);
                    free(temp);
                    free(temp2);

                }
                else // na socketu se stalo neco spatneho
                {
                    //number_of_clients--;
                    START_RECONNECT:
                    close(fd);
                    FD_CLR(fd, &player_socks);



                    //vynulování socketu hráče, který se odpojil
                    if (hra->p1_socket == fd){
                        hra->p1_socket = 0;
                    } else {
                        hra->p2_socket = 0;
                    }

                    if(fd == hra->your_turn){
                        hra->your_turn = 0;
                    }


                    if(hra->p1_socket == 0 && hra->p2_socket == 0){
                        printf("HRA č.%d: Druhý klient se odpojil ze hry!\n", hra->game_id);
                    }else{
                        printf("HRA č.%d: Klient se odpojil ze hry. Čekám %d vteřin na reconnect klienta!\n", hra->game_id, time_to_reconnect);
                        //vytvoření vlákna pro kontrolu opětovného připojení do hry
                        pthread_create(&reconnect_thread, NULL, &reconnect_checker, (void *)hra);
                    }
                }
            }
        }
    }
    sleep(2);
    count_of_games--;
    list_remove(list_search(&allgames, hra->game_id));
    pthread_exit(0);
}

/**
 * Funkce, která kotroluje, zda je zpráva výstřelu validní.
 * Zda už na tuto pozici nestřílel apod.
 * @param message  přijatá zpráva
 * @param hra      ukazatel na strukturu hry
 * @param socket   číslo socketu odkud byla zpráva odeslána
 * @return
 */
int fire(char *message, game *hra, int socket){
    char *temp;
    char response[20];
    int x,y;
    char xc[2], yc[2];
    strtok(message, ";");
    x = atoi(strtok(NULL, ";"));
    y = atoi(strtok(NULL, ";"));
    strcpy(response, "enemy;");
    sprintf(xc, "%d", x);
    sprintf(yc, "%d", y);


    if (hra->p1_socket == socket){      //zpráva byla prijata od hrace p1 a strilim do p2
        if(hra->p2_pane[x][y] == 1){
            hra->p2_pane[x][y] = 2;
            strcat(response, "1");  // 1 == zasah
            strcat(response, ";");
            strcat(response, xc);
            strcat(response, ";");
            strcat(response, yc);
            strcat(response, ";");
            send(hra->p2_socket, response, 12, 0);
            log_counter(12);
            return 1;                   //zasah
        }
        if (hra->p2_pane[x][y] == 0){
            hra->p2_pane[x][y] = 3;
            strcat(response, "0");  // 0 == vedle
            strcat(response, ";");
            strcat(response, xc);
            strcat(response, ";");
            strcat(response, yc);
            strcat(response, ";");
            send(hra->p2_socket, response, 12, 0);
            log_counter(12);
            return 0;                   //netrefil jsi se!
        }

        if (hra->p2_pane[x][y] == 2 || hra->p2_pane[x][y] == 3){
            return 2;                   //tady už jsi střílel
        }
    }

    if (hra->p2_socket == socket){      //zpráva byla prijata od hrace p2 a strilim do p1
        if(hra->p1_pane[x][y] == 1){
            hra->p1_pane[x][y] = 2;
            strcat(response, "1");  // 0 == vedle
            strcat(response, ";");
            strcat(response, xc);
            strcat(response, ";");
            strcat(response, yc);
            strcat(response, ";");
            send(hra->p1_socket, response, 12, 0);
            log_counter(12);
            return 1;                   //zasah
        }
        if (hra->p1_pane[x][y] == 0){
            hra->p1_pane[x][y] = 3;
            strcat(response, "0");  // 0 == vedle
            strcat(response, ";");
            strcat(response, xc);
            strcat(response, ";");
            strcat(response, yc);
            strcat(response, ";");
            send(hra->p1_socket, response, 12, 0);
            log_counter(12);
            return 0;                   //netrefil jsi se!
        }

        if (hra->p1_pane[x][y] == 2 || hra->p1_pane[x][y] == 3){
            return 2;                   //tady už jsi strílel!
        }
    }

    return -1;
}

//zkontroluje, zda je zpráva validní a vratí odpovídající hodnotu
//1 -> zpráva je validní
//0 -> zpráva není valdiní
/**
 * Zkontroluje příchozí zprávu. Pokud je validní tak vrací 1 a pokud
 * není validní vrací 0.
 * @param message   přijatá zpráva
 * @return          výsledek validace
 */
int is_valid_message(char *message){

    char *temp;
    int number_temp;
    int count = 0;

    temp = strtok(message, ";");

    if(strcmp(temp, "grid") == 0){
        temp = strtok(NULL, ";");
        while (temp != NULL){

            if(isalpha(temp[0]) != 1 && strlen(temp) == 1){

                number_temp = atoi(temp);
                //zda je volidní pozice v poli
                if(number_temp <= 5 && number_temp>=0){
                    count++;
                    //když je všech 12 hodnot validních
                    if(count == 12){
                        return 1;

                    }
                } else{
                    return 0;
                }
            }else {
                return 0;
            }
            temp = strtok(NULL, ";");
        }
        return 0;
    }

    if(strcmp(temp, "fire") == 0){
        temp = strtok(NULL, ";");
        while (temp != NULL){

            if(isalpha(temp[0]) != 1 && strlen(temp) == 1){

                number_temp = atoi(temp);
                //zda je volidní pozice v poli
                if(number_temp <= 5 && number_temp>=0){
                    count++;
                    //když je všech 12 hodnot validních
                    if(count == 2){
                        return 1;

                    }
                } else{
                    return 0;
                }
            }else {
                return 0;
            }
            temp = strtok(NULL, ";");
        }
        return 0;
    }

    if(strcmp(temp, "sync") == 0){
        return 1;
    }

    if(strcmp(temp, "ping") == 0){
        return 1;
    }


    return 0;
}

/**
 * Funkce pro nastavení lodí do dvourozměrného pole.
 * @param grid_message  zpráva pro nastavení gridu
 * @param hra           struktura hry
 * @param socket        číslo socketu hráče, který chce nastavit grid
 * @return              výsledek nastavení gridu
 */
int set_ships(char *grid_message, game *hra, int socket){

    int x,y;
    strtok(grid_message, ";");

    if (hra->p1_socket == socket){
        for (int i = 0; i < 6; ++i) {
            x = atoi(strtok(NULL, ";"));
            y = atoi(strtok(NULL, ";"));
            if (hra->p1_pane[x][y] == 0){
                hra->p1_pane[x][y] = 1;
            } else{
                for (int i = 0; i < 6; ++i) {
                    for (int j = 0; j < 6; ++j) {
                        hra->p1_pane[i][j] = 0;
                    }
                }
                return 0;
            }

        }
        return 1;
    }

    if (hra->p2_socket == socket){
        for (int i = 0; i < 6; ++i) {
            x = atoi(strtok(NULL, ";"));
            y = atoi(strtok(NULL, ";"));
            if (hra->p2_pane[x][y] == 0){
                hra->p2_pane[x][y] = 1;
            } else{
                for (int i = 0; i < 6; ++i) {
                    for (int j = 0; j < 6; ++j) {
                        hra->p2_pane[i][j] = 0;
                    }
                }
                return 0;
            }
        }
        return 1;
    }
    return 0;
}
/**
 * Pokud pošle hráč po reconnectu požadavek na synchornizaci,tak tato
 * funkce vytvoří synchronizační zprávu na základě aktuálního stavu hry.
 * @param game      ukazatel na strukturu aktuální hry
 * @param socket    socket pro rozeznání hráče
 * @return          vytvořená zpráva
 */
char *make_sync_message(game *game, int socket){

    char *message = calloc(sizeof(char)*170, 1);
    char temp[2];
    int mask;
    strcpy(message, "sync;set;");

    if(game->your_turn == socket){
        strncat(message, "your_turn;", 10);
    }else{
        strncat(message, "enemy_turn;", 11);
    }

    //odeslání pozic hráče, který se odpojil
    for (int i = 0; i < 6; ++i) {
        for (int j = 0; j < 6; ++j) {
            if (game->p1_socket == socket){
                sprintf(temp, "%d", game->p1_pane[i][j]);
                strncat(message, temp, 1);
                strncat(message, ";", 1);
                //musím poslat nejdříve grid prvního hráče a potom druhého

            } else if (game->p2_socket == socket){
                sprintf(temp, "%d", game->p2_pane[i][j]);
                strcat(message, temp);
                strcat(message, ";");
                //musím nejdříve poslat grid druhého hráče a potom prvního
            }
        }

    }
    //přidání počtu životů za pole hráče
    if (game->p1_socket == socket){
        sprintf(temp, "%d", game->p1_ships);
        strncat(message, temp, 1);
        strncat(message, ";", 1);
    } else if (game->p2_socket == socket){
        sprintf(temp, "%d", game->p2_ships);
        strncat(message, temp, 1);
        strncat(message, ";", 1);
        //musím nejdříve poslat grid druhého hráče a potom prvního
    }
    //odeslání protihráčových pozic
    for (int i = 0; i < 6; ++i) {
        for (int j = 0; j < 6; ++j) {
            if (game->p1_socket == socket){
                mask = game->p2_pane[i][j];
                //pokud je to lod, tak poslat protihráči vodu
                if(mask == 1){
                    strncat(message, "0", 1);
                    strncat(message, ";", 1);
                } else{
                    sprintf(temp, "%d", game->p2_pane[i][j]);
                    strncat(message, temp, 1);
                    strncat(message, ";", 1);
                }
                //musím poslat nejdříve grid prvního hráče a potom druhého

            } else if (game->p2_socket == socket){
                mask = game->p1_pane[i][j];
                //pokud je to lod, tak poslat protihráči vodu
                if(mask == 1){
                    strncat(message, "0", 1);
                    strncat(message, ";", 1);
                } else{
                    sprintf(temp, "%d", game->p1_pane[i][j]);
                    strncat(message, temp, 1);
                    strncat(message, ";", 1);
                }
            }
        }

    }

    //přidání počtu životů za pole protihráče
    if (game->p1_socket == socket){
        sprintf(temp, "%d", game->p2_ships);
        strncat(message, temp, 1);
        strncat(message, ";", 1);
    } else if (game->p2_socket == socket){
        sprintf(temp, "%d", game->p1_ships);
        strncat(message, temp, 1);
        strncat(message, ";", 1);
        //musím nejdříve poslat grid druhého hráče a potom prvního
    }

    return message;
}

