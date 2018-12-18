//
// Created by Ondřej Váně on 11.12.18.
//

#include <stdio.h>
#include <sys/socket.h>
#include <sys/select.h>
#include <pthread.h>
#include <stdlib.h>
#include "game.h"
#include "loger.h"


void *ping(void *args) {

    game *hra;
    hra = (game *) args;

    while (hra->is_running){
        sleep(30);
        for (int i = 0; i<30; i++){
            sleep(1);
            if(hra->is_running == 0){
                printf("PING č.%d: ukončuji ping vlákno\n", hra->game_id);
                pthread_exit(0);
            }
        }

        int ret1, ret2;


        printf("PING č.%d: Odesláno P1: %d a P2: %d\n", hra->game_id, hra->p1_socket, hra->p2_socket);
        ret1 = send(hra->p1_socket, "ping;", 5, 0);
        ret2 = send(hra->p2_socket, "ping;", 5, 0);


        sleep(10);



        if(hra->p1_is_reachable == 0 && ret1 > 0){
            hra->p1_is_reachable = -1;
            printf("PING %d: P1 socket zavřen\n", hra->game_id);
        } else{
            hra->p1_is_reachable = 0;
        }

        if(hra->p2_is_reachable == 0 && ret2 > 0){
            hra->p2_is_reachable = -1;
            printf("PING %d: P2 socket zavřen\n", hra->game_id);
        } else{
            hra->p2_is_reachable = 0;
        }

    }
    return 0;
}