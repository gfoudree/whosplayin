//
// Created by gfoudree on 2/3/16.
//

#ifndef CHATSERVER_CHATSERVER_H
#define CHATSERVER_CHATSERVER_H

#include <iostream>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <netdb.h>
#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/x509v3.h>
#include <openssl/bn.h>
#include <openssl/asn1.h>
#include <openssl/x509.h>
#include <openssl/x509_vfy.h>
#include <openssl/pem.h>
#include "User.h"
#include "ChatHandler.h"
#include <unistd.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <arpa/inet.h>
#include <boost/thread/thread.hpp>
#include <boost/bind/bind.hpp>

using namespace std;

class ChatServer {

private:
    SSL_CTX *ctx;
    const SSL_METHOD *method;
    EC_KEY *ecdh;
    sockaddr_in sockInfo;
    int serverSock = 0;
    boost::thread_group threads;
    std::vector<User> activeUsers;
    std::vector<ChatHandler*> chatHandlers;

public:
    void init();
    ChatServer(int port, const char *cacert, const char *cert, const char *key);
    ~ChatServer();
};


#endif //CHATSERVER_CHATSERVER_H
