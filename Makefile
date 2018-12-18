all: client_java server_c

server_c:
	cd c_server && make && mv server ../ && echo "\n### Server úspěšně přeložen ###\n"

client_java:
	cd java_client && cd BattleShipClient && ant && mv ./out/artifacts/BattleShipClient_jar/BattleShipClient.jar ../.. && echo "\n### Client úspěšně přeložen! ###\n"

clean: clear

clear:
	cd c_server && cd BattleShipServerLinux && make clean