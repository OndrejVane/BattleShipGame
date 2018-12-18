//
// Created by Ondřej Váně on 21.11.18.
//

#include "game.h"
#include "global_variables.h"
#include <sys/types.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

/**
 * Funkce pro přidání nové hry do spojového seznamu her a inicializace
 * všech hodnot pro hru.
 * @param head          ukazatel na první prvek ve spojovém seznamu
 * @param p1_socket     číslo socketu 1.hráče
 * @param p2_socket     číslo socketu 2.hráče
 * @param p1_nick       nick 1.hráče
 * @param p2_nick       nick 2.hráče
 * @return
 */
game *add_game(game **head, int p1_socket, int p2_socket, char *p1_nick, char *p2_nick) {
    game *newgame, *p;

    /* inicializace nove hry */
    newgame = malloc(sizeof(game));		    // alokace pameti pro strukturu
    newgame->game_id = last_game_id; 	    // ID hry
    last_game_id++;
    newgame->p1_socket = p1_socket;         // socket 1. hrace
    newgame->p2_socket = p2_socket;         // socket 2. hrace
    strncpy(newgame->p1_nick, p1_nick, 32); //překopírování nicku 1.hráče
    strncpy(newgame->p2_nick, p2_nick, 32); //překopírování nicku 2.hráče
    newgame->p1_ships = 6;                  //počet lodí 1.hráče
    newgame->p2_ships = 6;                  //počet lodí 2.hráče
    newgame->p1_set = 0;                    //zda má 1.hráč nastavené lodě (1=nastaveny | 0=nenastaveny)
    newgame->p2_set = 0;                    //zda má 2.hráč nastavené lodě (1=nastaveny | 0=nenastaveny)
    newgame->p1_reconnect = 0;              //pomocná proměnná pro uložení socketu po reconnectu pro 1.hráče
    newgame->p2_reconnect = 0;              //pomocná proměnná pro uložení socketu po reconnectu pro 2.hráče
    newgame->p1_is_reachable = 0;
    newgame->p2_is_reachable = 0;
    newgame->is_running = 1;                //1 = zda hra běží nebo 0 = hra už může skončit
    newgame->next = NULL;
    //newgame->your_turn = p1_socket;


    //inicializa polí pro hru!!
    for (int i = 0; i < 6; ++i) {
        for (int j = 0; j < 6; ++j) {
            newgame->p1_pane[i][j] = 0;
            newgame->p2_pane[i][j] = 0;
        }
    }

    // zarazeni hry do seznamu her
    if (*head) {
        p = *head;
        while (p->next) p = p->next;
        p->next = newgame;
    }
    else {
        *head = newgame;
    }
    return newgame;
}

/**
 * Funkce pro odstranění všech her ze spojového
 * seznamu
 * @param p ukazatel na struktur první hry
 */
void list_remove (game **p){
    if (p && *p) {
        game *n = *p;
        *p = (*p)->next;
        free(n);
    }
}

/**
 * Nalezení kdy podle jejího id.
 *
 * @param n     ukazatel na první prvek ve spojovém seznamu
 * @param id    id hry, která má být vyhledána
 * @return      ukazatel a hledanou hru
 */
game **list_search (game **n, int id){
    if (n) {
        while (*n) {
            if ((*n)->game_id == id) return n;
            n = &(*n)->next;
        }
    }
    return NULL;
}


/**
 * Funkce, která vyhledá hru podle toho zda obsahuje zadanný nick.
 *
 * @param n     ukazatel na první prvek ve spojovém seznamu
 * @param nick  nick, který má být vyhledán
 * @return      pokud je hra nalezena vrát ukazatel na hru pokud ne tak null
 */
game **list_search_nick (game **n, char *nick){
    if (n) {
        while (*n) {
            if (strcmp((*n)->p1_nick, nick) == 0 || strcmp((*n)->p2_nick, nick) == 0){
                return n;
            }
            n = &(*n)->next;
        }
    }
    return NULL;
}

/**
 * Funkce pro výpis všech her, které jsou ve spojovém seznamu.
 * @param head      ukazatel na první prvek v seznamu
 */
void print_games(game *head) {
    if(!head){
        printf("Zadna hra neni spustena\n");
    } else{
        while(head) {
            printf("GAME #%d: %s vs. %s (%d:%d)\n", head->game_id, head->p1_nick, head->p2_nick, head->p1_ships, head->p2_ships);
            head = head->next;
        }
        if(!head){
            printf("Zadna dalsi hra neni spustena\n");
        }
    }

}
