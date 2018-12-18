//
// Created by Ondřej Váně on 21.11.18.
//

#include <time.h>
#include <semaphore.h>
#include <string.h>
#include <stdlib.h>
#include "global_variables.h"

//semafor pro ošetření souběhu nad počítadlem zpráv
sem_t mutex;

/**
 * Funkce, která inicialuzuje log a zapíše do souboru
 * informace o startu serveru.
 */
void log_server_init(){
    time_t cas;
    struct tm *ltmcas;
    cas = time(NULL);
    ltmcas = localtime(&cas);

    sem_init(&mutex, 0, 1); // 1. parametr 0 => pokud je semafor mezi vlánky (1 pokud je mezi procesy)
                                    // 2. parametr inicializační hodnota semfaru

    time_start = time(NULL);
    log_server = fopen("server.log", "w");
    fclose(log_server);
    log_server = fopen("server.log", "a+");

    fprintf(log_server,"Server startuje v case %s", ctime(&cas));
    fprintf(log_server,"Server bezi na adrese %s a portu %d.\n", local_ip, server_port);



    fclose(log_server);
}

/**
 * Funkce pro zapsání zprávy do logu.
 * @param msg zpráva
 */
void log_server_add(char *msg) {
    log_server = fopen("server.log", "a+");
    fprintf(log_server,"%s",msg);
    fclose(log_server);
}

/**
 * Funkce, která počítá počet odeslaných zpráv a
 * zároveň počet odeslaných bytů. Souběh ošetřen pomocí semeforu.
 *
 * @param number_of_bytes   Počet odelsaných bitů.
 */
void log_counter(int number_of_bytes){

    sem_wait(&mutex);
    log_msg_send++;
    log_byte_send = log_byte_send + number_of_bytes;
    sem_post(&mutex);
}
/**
 * Funkce která počítá přijaté zprávy a počet
 * přijatých bytů. Souběh ošetřen pomocí ssemaforu.
 * @param number_of_bytes
 */
void log_counter_recieve(int number_of_bytes){

    sem_wait(&mutex);
    log_msg_recv++;
    log_byte_recv = log_byte_recv + number_of_bytes;
    sem_post(&mutex);
}
/**
 * Funkce pro ukončení logeru. Zapsání náležitých informací a
 * statistik do legu a uzavření souboru. Zničení
 */
void log_server_over(){
    time_t cas;
    struct tm *ltmcas = localtime(&cas);
    cas = time(NULL);
    int total_time;

    log_server = fopen("server.log", "a+");

    time_close = time(NULL);
    total_time = (int) difftime(time_close,time_start);

    fprintf(log_server,"Server byl ukoncen v case %s", ctime(&cas));
    fprintf(log_server,"Server bezel %d sekund.\n", total_time);
    fprintf(log_server,"%s\n",cli_delim);
    fprintf(log_server,"STATISTIKA\n");
    fprintf(log_server,"%s\n",cli_delim);
    fprintf(log_server, "Celkovy pocet odehranych her: %d\n", last_game_id-1);
    fprintf(log_server, "Celkovy pocet pripojenych klientu: %d\n", number_of_clients);
    fprintf(log_server, "Celkovy pocet odeslanych zprav: %ld\n", log_msg_send);
    fprintf(log_server, "Celkovy pocet odeslanych bytu: %ld\n", log_byte_send);
    fprintf(log_server, "Celkovy pocet prijatych zprav: %ld\n", log_msg_recv);
    fprintf(log_server, "Celkovy pocet prijatych bytu: %ld\n", log_byte_recv);
    sem_destroy(&mutex);
}
