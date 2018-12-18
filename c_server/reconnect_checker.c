//
// Created by Ondřej Váně on 22.11.18.
//

#include <pthread.h>
#include <sys/socket.h>
#include "game.h"
#include "global_variables.h"
#include "time_checker.h"
#include "loger.h"

/**
 * Vlákno, které se spustí pokud se jeden z hráčů odpojí ze hry.
 * Toto vlákno kontroluje, zda se hráč vrátí do hry. Pokud se nevrátí
 * do časového limitu, je protihráč informován a hra je ukončena.
 *
 * @param args  argument pro předání ukazatele na struktury hry.
 * @return
 */
void *reconnect_checker(void *args){
    game *hra;
    hra = (game *)args;

    for (int i = 0; i < time_to_reconnect; ++i) {
        wait_for(1);
	
        if(hra-> p1_reconnect != 0){
            hra->p1_socket = hra->p1_reconnect;
            FD_SET(hra->p1_socket, hra->player_socks);
            printf("HRA č.%d: Hráč %d se vrátil do hry.\n", hra->game_id, hra->p1_socket);
            hra->p1_reconnect = 0;

        }

        if(hra -> p2_reconnect !=0){
            hra->p2_socket = hra->p2_reconnect;
            FD_SET(hra->p2_socket, hra->player_socks);
            printf("HRA č.%d: Hráč %d se vrátil do hry.\n", hra->game_id, hra->p2_socket);
            hra->p2_reconnect = 0;
        }

        if (hra->p1_socket != 0 && hra->p2_socket != 0){
            printf("HRA č.%d: Oba hráči jsou zpět ve hře.\n", hra->game_id);
            pthread_exit(0);
        }
    }
    if(hra->p1_socket == 0 && hra->p2_socket == 0){
        printf("HRA č.%d: Hra ukončena. Ani jeden z hráču se nevrátil do hry po %d s.\n", hra->game_id, time_to_reconnect);
        hra->is_running = 0;
        pthread_exit(0);
    }else if(hra->p1_socket == 0){
        send(hra->p2_socket, "result;disconnect;", 18, 0);
        log_counter(18);
        printf("HRA č.%d: Protihráč se nepřipojil do %d s. Hra ukončena.\n", hra->game_id, time_to_reconnect);
        FD_SET(hra->p2_socket, &client_socks);
        FD_CLR(hra->p2_socket, hra->player_socks);
        hra->is_running = 0;
        pthread_exit(0);
    }else if(hra->p2_socket == 0){
        send(hra->p1_socket, "result;disconnect;", 18, 0);
        log_counter(18);
        printf("HRA č.%d: Protihráč se nepřipojil do %d s. Hra ukončena.\n", hra->game_id, time_to_reconnect);
        FD_SET(hra->p1_socket, &client_socks);
        FD_CLR(hra->p1_socket, hra->player_socks);
        hra->is_running = 0;
        pthread_exit(0);
    }
    return 0;
}
