//
// Created by gfoudree on 2/3/16.
//

#ifndef CHATSERVER_CHATHANDLER_H
#define CHATSERVER_CHATHANDLER_H

#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/x509v3.h>
#include <openssl/bn.h>
#include <openssl/asn1.h>
#include <openssl/x509.h>
#include <openssl/x509_vfy.h>
#include <openssl/pem.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <vector>
#include "User.h"


class ChatHandler {
public:
    int socket;
    sockaddr_in cliInfo;
    SSL *ssl;

    void chatHandler(std::vector<User> *users);
    ChatHandler(int sock, sockaddr_in info, SSL *pSsl);
    ~ChatHandler();
};


#endif //CHATSERVER_CHATHANDLER_H
