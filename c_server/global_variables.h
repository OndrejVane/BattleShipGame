//
// Created by Ondřej Váně on 21.11.18.
//

#include <sys/types.h>
#include <sys/select.h>
#include <stdio.h>

#ifndef _GLOBAL_VARIABLES_H
#define _GLOBAL_VARIABLES_H

extern int BUFFSIZE;
extern int server_port;
extern char *cli_delim;
extern char *local_ip;
extern int client_socket_buffer[2];
extern int number_of_clients;
extern int time_to_wait;
extern int time_to_reconnect;
extern int count_of_wrong_message;
extern fd_set client_socks;
extern struct Game *allgames;
extern int max_count_of_game;
extern int count_of_games;
extern int last_game_id;
extern FILE *log_server;
extern int ip_set_flag;
extern char *ip_address;
extern time_t time_start, time_close;
extern long log_msg_send;		/* pocet celkove odeslanych zprav */
extern long log_msg_recv;		/* pocet celkove prijatych zprav */
extern long log_byte_send;		/* pocet celkove odeslanych bytu */
extern long log_byte_recv;

#endif