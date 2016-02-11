all : ssl_server

ssl_server : ssl_server.c
	clang ssl_server.c -lssl lib/libcrypto.a -g -o sslserver
