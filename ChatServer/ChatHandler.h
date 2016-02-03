//
// Created by gfoudree on 2/3/16.
//

#ifndef CHATSERVER_CHATHANDLER_H
#define CHATSERVER_CHATHANDLER_H

#include <netinet/in.h>  // Internet domain address structures and functions
#include <sys/socket.h>  // Structures and functions used for socket API
#include "User.h"


class ChatHandler {
public:
    int socket;
    sockaddr_in cliInfo;

    void chatHandler(std::vector<User> *users);
};


#endif //CHATSERVER_CHATHANDLER_H
