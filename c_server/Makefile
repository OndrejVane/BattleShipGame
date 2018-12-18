#
# Makefile server for Battleship game
#

CC = gcc
C99 = -std=c99

server : main.o game.o game_controller.o global_variables.o loger.o reconnect_checker.o server.o server_listener.o time_checker.o ping.o
	$(CC) -l pthread main.o game.o game_controller.o global_variables.o loger.o reconnect_checker.o server.o server_listener.o time_checker.o ping.o -o server

main.o : main.c
	$(CC) $(C99) -c -w main.c

game.o : game.c
	$(CC) $(C99) -c -w game.c

game_controller.o : game_controller.c
	$(CC) $(C99) -c -w game_controller.c

global_variables.o : global_variables.c
	$(CC) $(C99) -c -w global_variables.c

loger.o : loger.c
	$(CC) $(C99) -c -w loger.c -pthread

reconnect_checker.o : reconnect_checker.c
	$(CC) $(C99) -c -w reconnect_checker.c

server.o : server.c
	$(CC) $(C99) -c -w server.c

server_listener.o : server_listener.c
	$(CC) $(C99) -c -w server_listener.c

time_checker.o : time_checker.c
	$(CC) $(C99) -c -w time_checker.c

ping.o : ping.c
	$(CC) $(C99) -c -w ping.c

clean :
	rm main.o game.o game_controller.o global_variables.o loger.o reconnect_checker.o server.o server_listener.o time_checker.o ping.o
