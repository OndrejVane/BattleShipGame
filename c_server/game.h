//
// Created by Ondřej Váně on 21.11.18.
//

#include <sys/types.h>
#include <sys/select.h>

#ifndef _GAME_H
#define _GAME_H

typedef struct Game {
    /* Basic information about game */
    int game_id;
    char p1_nick[32], p2_nick[32];
    int p1_socket, p2_socket;
    int p1_ships, p2_ships;
    int p1_pane[6][6], p2_pane[6][6];
    int p1_set, p2_set;     //kontrola, zda oba poslaly rozmístění lodí
    int p1_reconnect, p2_reconnect;
    int p1_is_reachable, p2_is_reachable;
    int your_turn;   //zde bude uložen socket hrace, ktery je na tahu
    int is_running;
    fd_set *player_socks;
    /* Next element of linked list*/
    struct Game *next;
}game;

game *add_game(game **head, int p1_socket, int p2_socket, char *p1_nick, char *p2_nick);

void list_remove (game **p);

game **list_search (game **n, int id);

game **list_search_nick (game **n, char *nick);

void print_games(game *head);

#endif