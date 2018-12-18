//
// Created by Ondřej Váně on 21.11.18.
//

#include <sys/types.h>
#include <sys/select.h>
#include <stdio.h>

int BUFFSIZE = 100;
int server_port = 10000;
char *cli_delim = "----------------------------------------\n";
char *local_ip;
int client_socket_buffer[2] = {0, 0};
int number_of_clients = 0;
int time_to_wait = 30;
int time_to_reconnect = 120;
int count_of_wrong_message = 3;
fd_set client_socks;
struct Game *allgames;
int last_game_id = 1;
int ip_set_flag = 0;
char *ip_address;
int max_count_of_game = 3;
int count_of_games = 0;


FILE *log_server;
time_t time_start, time_close;
long log_msg_send = 0;		/* pocet celkove odeslanych zprav */
long log_msg_recv = 0;		/* pocet celkove prijatych zprav */
long log_byte_send = 0;		/* pocet celkove odeslanych bytu */
long log_byte_recv = 0;     /* pocet celkove přijatých bytu */
