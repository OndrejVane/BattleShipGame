//
// Created by Ondřej Váně on 21.11.18.
//

#include "game.h"

#ifndef _GAME_CONTROLLER_H
#define _GAME_CONTROLLER_H

void *game_controller(void *args);

int fire(char *message, game *hra, int socket);

int is_valid_message(char *message);

int set_ships(char *grid_message, game *hra, int socket);

char *make_sync_message(game *game, int socket);

#endif