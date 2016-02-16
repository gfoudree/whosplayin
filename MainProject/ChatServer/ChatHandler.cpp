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
    X509 *cliCert;
    const char *welcomeMsg = "Hello, welcome to the Who'splayin chat interface!\n";

    cliCert = SSL_get_peer_certificate(ssl); //Get the peer's certifiate so we can verify identity

    //Suppose you can do some CA checking here, make sure it was by our CA
    if (cliCert == NULL)
        printf("Client did not supply certificate\n");
    else {
        char *subj, *issuer;
        subj = X509_NAME_oneline(X509_get_subject_name(cliCert), 0, 0);
        issuer = X509_NAME_oneline(X509_get_issuer_name(cliCert), 0, 0);
        printf("Subject: %s\nIssuer: %s\n", subj, issuer); //Print out certificate info
        free(subj);
        free(issuer);
    }

    SSL_write(ssl, welcomeMsg, strlen(welcomeMsg)); //Send the hello message

    do {
        memset(buf, 0, sizeof(buf));
        recvBytes = SSL_read(ssl, buf, sizeof(buf) - 1);
        std::string data(buf);

        std::cout << data.c_str(); //Print out the data recieved

        if (std::regex_match(data.c_str(), std::regex("JOIN \\w+((\\r)|(\\n))?*"))) //Are they sending a join command?
        {
            std::string name = data.substr(5, data.find('\n', 0));
            std::cout << "Hello " << name.c_str() << std::endl;
            SSL_write(ssl, "Hello", 5);

            User newUser;
            newUser.name = name;

            users->push_back(newUser); //Add the users to the list of logged in users
        }
        else if (std::regex_match(data, std::regex("EXIT \\w+((\\r)|(\\n))?*"))) //Are they sending an exit command?
        {
            std::string name = data.substr(5, data.find('\n', 0));
            std::cout << "Goodbye " << name.c_str() << std::endl;
            SSL_write(ssl, "Goodbye", 7);
            break;
        }
        else if(data.compare("LS_USERS") == 0) //List all the current users
        {
            std::string response;
            for (std::vector<User>::iterator it = users->begin(); it != users->end(); ++it)
            {
                response.append(it->name);
            }
            SSL_write(ssl, response.c_str(), response.length());
        }
    }
    while(recvBytes > 0); //Do this loop until the client disconnects
    std::cout << "Client disconnected." << std::endl;
    X509_free(cliCert);
    SSL_shutdown(ssl);
    close(socket);
    SSL_free(ssl);
}

ChatHandler::ChatHandler(int sock, sockaddr_in info, SSL *pSsl) {
    socket = sock;
    cliInfo = info;
    ssl = pSsl;
}

ChatHandler::~ChatHandler() {
    SSL_shutdown(ssl);
    close(socket);
    SSL_free(ssl);
}
