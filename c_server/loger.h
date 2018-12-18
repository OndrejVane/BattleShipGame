//
// Created by Ondřej Váně on 21.11.18.
//

#ifndef _LOGER_H
#define _LOGER_H

void log_server_init();

void log_server_add(char *msg);

void log_server_over();

void log_counter(int number_of_bytes);

void log_counter_recieve(int number_of_bytes);

#endif