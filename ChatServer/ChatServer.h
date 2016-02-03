//
// Created by gfoudree on 2/3/16.
//

#ifndef CHATSERVER_CHATSERVER_H
#define CHATSERVER_CHATSERVER_H

#include <iostream>
#include <sys/types.h>   // Types used in sys/socket.h and netinet/in.h
#include <netinet/in.h>  // Internet domain address structures and functions
#include <sys/socket.h>  // Structures and functions used for socket API
#include <netdb.h>       // Used for domain/DNS hostname lookup
#include "User.h"
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
    sockaddr_in sockInfo;
    int hSock = 0;
    boost::thread_group threads;
    std::vector<User> activeUsers;

public:
    void init();
    ChatServer(int port);
    ~ChatServer();
};


#endif //CHATSERVER_CHATSERVER_H
