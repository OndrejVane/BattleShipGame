//
// Created by Ondřej Váně on 22.11.18.
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
#include "global_variables.h"
#include "server.h"
#include "loger.h"
#include "main.h"

int main(int argc, char *argv[]) {

    //zpracování parametrů
    process_parameters(argc, argv);

    //strat vlánka pro přípojení hráčů
    start_server();



}

/**
 * Funkce, která zpracouje vstupní paramtery při spuštění serveru.
 * @param argc  počet vstupních parametrů
 * @param argv  pole vstupních parametrů
 * @return
 */
int process_parameters(int argc, char *argv[]){
    int i, j;
    i = argc;
    j = 1;
    while (i > 1) {
        if (strcasecmp(argv[j], "-h") == 0 || strcasecmp(argv[j], "-help") == 0) {
            get_help();
            exit(0);
        } else if ((strcasecmp(argv[j], "-port") == 0 || strcasecmp(argv[j], "-p") == 0) && argv[j + 1] != NULL) {
            int pom;
            pom = atoi(argv[j + 1]);
            if (pom >= 1 && pom <= 65535) {
                server_port = pom;
                printf("Nastaven port na %d\n", server_port);
            } else {
                printf("Nastaveni portu je chybne! Zustava na %d\n", server_port);
            }
            i = i - 2;
            j = j + 2;
            continue;
        } else if ((strcasecmp(argv[j], "-wait") == 0 || strcasecmp(argv[j], "-w") == 0) && argv[j + 1] != NULL) {
            int pom;
            pom = atoi(argv[j + 1]);
            if (pom >= 10 && pom <= 300) {
                time_to_wait = pom;
                printf("Nastavena doba cekani na pripojeni druheho hrace na %d\n", time_to_wait);
            } else {
                printf("Nastaveni doby cekani na pripojeni druheho hrace je chybne! Zustava na %d\n", time_to_wait);
            }
            i = i - 2;
            j = j + 2;
            continue;
        } else if ((strcasecmp(argv[j], "-reconnect") == 0 || strcasecmp(argv[j], "-r") == 0) && argv[j + 1] != NULL) {
            int pom;
            pom = atoi(argv[j + 1]);
            if (pom >= 60 && pom <= 300) {
                time_to_reconnect = pom;
                printf("Nastavena doba cekani na pripojeni druheho hrace na %d\n", time_to_reconnect);
            } else {
                printf("Nastaveni doby cekani na pripojeni druheho hrace je chybne! Zustava na %d\n", time_to_reconnect);
            }
            i = i - 2;
            j = j + 2;
            continue;
        }else if ((strcasecmp(argv[j], "-address") == 0 || strcasecmp(argv[j], "-a") == 0) && argv[j + 1] != NULL) {

            ip_address = argv[j + 1];

            if(isValidIpAddress(ip_address)){
                printf("Nastavena IP adresa serveru na %s\n", ip_address);
                ip_set_flag = 1;
            } else{
                printf("Nastavení IP adresy se nezdařilo. Zustává původní nastavení.\n");
            }

            i = i - 2;
            j = j + 2;
            continue;
        }else if ((strcasecmp(argv[j], "-count") == 0 || strcasecmp(argv[j], "-c") == 0) && argv[j + 1] != NULL) {
            int pom;
            pom = atoi(argv[j + 1]);

            if (pom > 0 && pom < 7) {
                max_count_of_game = pom;
                printf("Nastaven maximalni pocet her na %d\n", max_count_of_game);
            } else {
                printf("Nastaveni maximalniho poctu her je chybne. Zustava na %d\n", max_count_of_game);
            }

            i = i - 2;
            j = j + 2;
            continue;
        }
        i--;
    }
    return 0;
}

/**
 * Funkce pro vypsání nápovědy k parametrům serveru
 * při spuštění s parametrem -h.
 */
void get_help(){
    printf("%s\tSERVER BATTLESHIP HELP\n%s", cli_delim, cli_delim);
    printf("-a [ip adresa] \t nastavi ip adresu, na kterem bude server naslouchat.\n");
    printf("-p [cislo] \t nastavi port, na kterem bude server naslouchat (1-65535).\n");
    printf("-w [cislo] \t nastavi kolik vterin bude server cekat na pripojeni druheho hrace (10 - 300).\n");
    printf("-r [cislo] \t nastavi kolik vterin bude server cekat na reconnect hrace (60 - 300).\n");
    printf("-c [cislo] \t nastavi kolik her bude moct soubezne bezet(1-6).\n");
    printf("-h \t\t vypsani napovedy k paramterum.\n");

}
/**
 * Funkce pro generování ip adresy zadané uživatelem.
 * @param ipAddress
 * @return
 */
int isValidIpAddress(char *ipAddress)
{
    struct sockaddr_in sa;
    int result = inet_pton(AF_INET, ipAddress, &(sa.sin_addr));
    return result != 0;
}
