//
// Created by Ondřej Váně on 21.11.18.
//


#include "global_variables.h"
#include <time.h>
#include <sys/socket.h>
#include <pthread.h>
#include "loger.h"

/**
 * Funkce, která čaká x vteřin (sleep(x second))
 * @param secs  počet vteři pro uspání
 */
void wait_for (unsigned int secs) {
    /*
    unsigned int retTime = time(0) + secs;   // Get finishing time.
    while (time(0) < retTime);               // Loop until it arrives.
     */
    sleep(1*secs);
}
/**
 * Vlákno, které se spustí, když jeden hráč čeká na připojení protihráče.
 * Pokud není hráč nalezen do x vteřin, tak pošle hráči odpovídající zprávu.
 * Pokud je protihráč nalezen odešle hráčům opponent;found;
 * @param args
 * @return
 */
void *time_checker(void *args){

    char *temp;

    for(int i=0; i<=time_to_wait; i++){
        wait_for(1);

        if (client_socket_buffer[0] != client_socket_buffer[1]){
            if(client_socket_buffer[1] != 0){
                temp = "opponent;found;";
                send(client_socket_buffer[0],temp, 15,0);
                log_counter(15);
                send(client_socket_buffer[1],temp, 15,0);
                log_counter(15);
                pthread_exit(0);
            }
        }

        if (client_socket_buffer[0] == 0){
            pthread_exit(0);
        }

        if (client_socket_buffer[0] == client_socket_buffer[1]){
            temp = "opponent;not_found;";
            send(client_socket_buffer[0],temp, 19,0);
            log_counter(19);
            client_socket_buffer[0] = 0;
            client_socket_buffer[1] = 0;
        }
    }

    temp = "opponent;not_found;";
    send(client_socket_buffer[0],temp, 19,0);
    log_counter(19);
    client_socket_buffer [0] = 0;
    pthread_exit(0);
}
