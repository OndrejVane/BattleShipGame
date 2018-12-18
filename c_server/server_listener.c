//
// Created by Ondřej Váně on 21.11.18.
//

#include <stdio.h>
#include <strings.h>
#include <stdlib.h>
#include <signal.h>
#include "global_variables.h"
#include "game.h"
#include "loger.h"
#include "server_listener.h"



/**
 * Vlákno, které naslouchá příkazy z příkazové řádky pro
 * ovládání serveru za běhu.
 * @param arg  příkazy z příkazové řádky
 * @return
 */
void *server_listener(void *arg){

    signal(SIGINT, sigint_handler);

    char *command = calloc(sizeof(char)*BUFFSIZE, 1);
    while(1){
        printf(">>>");
        scanf("%s",command);
        if(strcasecmp(command, "exit")==0 || strcasecmp(command, "close")==0){
            log_server_add("Server ukoncen prikazem EXIT\n");
            log_server_over();
            printf("\nSERVER: Ukonceno prikazem EXIT\n");
            exit(0);
        }
        else if(strcmp(command, "game")==0){
            printf("\nBezici hry:\n");
            printf("%s", cli_delim);
            print_games(allgames);
        }
        else if(strcmp(command, "info")==0){
            printf("INFO: Server bezi na %s:%d\n", local_ip, server_port);
            printf("INFO: Server ceka na protihrace %d s.\n", time_to_wait);
            printf("INFO: Server ceka na reconnect %d s.\n", time_to_reconnect);
            printf("INFO: Maximalni pocet her je  %d. \n", max_count_of_game);

        }
        else if(strcmp(command, "help")==0){
            printf("exit (close): ukonci server\n");
            printf("game: vypise prave probihajici hry\n");
            printf("info: vypise informace o server\n");
        }
        else{
            printf("Neznamy prikaz. Pouzijte \"help\" pro napovedu.\n");
        }
        memset(command,0,strlen(command));

    }


}

/**
 *
 * Funkce detekujici signaly, konkretne ukonceni serveru klavesami CTRL+C
 */
void sigint_handler(int sig){
    log_server_add("Ukonceno pres CTRL+C\n");
    log_server_over();
    printf("\nSERVER: Detekovano stiknuti CTRL+C. Server ukoncen!\n");
    exit(1);
}



