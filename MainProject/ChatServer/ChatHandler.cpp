//
// Created by gfoudree on 2/3/16.
//

#include <unistd.h>
#include <iostream>
#include <string.h>
#include <regex>

#include "ChatHandler.h"
#include "User.h"


void ChatHandler::chatHandler(std::vector<User> *users) {
    char buf[512];
    int recvBytes = 0;

    do {
        memset(buf, 0, sizeof(buf));
        recvBytes = recv(socket, buf, sizeof(buf) - 1, 0);
        std::string data(buf);

        std::cout << data.c_str();

        if (std::regex_match(data.c_str(), std::regex("JOIN \\w+((\\r)|(\\n))?*"))) //Are they sending a join command?
        {
            std::string name = data.substr(5, data.find('\n', 0));
            std::cout << "Hello " << name.c_str() << std::endl;
            send(socket, "Hello", 5, 0);

            User newUser;
            newUser.name = name;

            users->push_back(newUser);
        }
        else if (std::regex_match(data, std::regex("EXIT \\w+((\\r)|(\\n))?*"))) //Are they sending an exit command?
        {
            std::string name = data.substr(5, data.find('\n', 0));
            std::cout << "Goodbye " << name.c_str() << std::endl;
            send(socket, "Goodbye", 7, 0);
            close(socket);
        }
        else if(data.compare("LS_USERS"))
        {
            std::string response;
            bool first = true;
            for (std::vector<User>::iterator it = users->begin(); it != users->end(); ++it)
            {
                if (first) {
                    response.append(", ");
                    first = false;
                }
                response.append(it->name);
            }
            send(socket, response.c_str(), response.length(), 0);
        }
    }
    while(recvBytes > 0);
    close(socket);
}