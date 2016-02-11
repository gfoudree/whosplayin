//
// Created by gfoudree on 2/3/16.
//

#include "ChatServer.h"
#include "ChatHandler.h"

ChatServer::~ChatServer() {
    threads.join_all();
    close(hSock);
}

ChatServer::ChatServer(int port) {
    memset(&sockInfo, 0, sizeof(sockaddr_in));

    sockInfo.sin_port = htons(port);
    sockInfo.sin_addr.s_addr = htonl(INADDR_ANY);
    sockInfo.sin_family = (AF_INET);
}

void ChatServer::init() {
    hSock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (hSock < 0) {
        close(hSock);
        throw "Error creating socket";
    }

    if (bind(hSock, (const sockaddr *)&sockInfo, sizeof(sockInfo)) < 0) {
        close(hSock);
        throw "Error binding";
    }

    if (listen(hSock, 5) < 0) {
        close(hSock);
        throw "Error listening";
    }

    while (1)
    {
        int hClientSock = 0;
        sockaddr_in clientInfo;
        socklen_t cliLen;

        cliLen = sizeof(clientInfo);
        memset(&clientInfo, 0, sizeof(sockaddr_in));

        hClientSock = accept(hSock, (sockaddr*)&clientInfo, &cliLen);

        if (hClientSock < 0)
        {
            cerr << "Error on accept\n";
            close(hClientSock);
        }

        cout << "Got connection from: " << inet_ntoa(clientInfo.sin_addr) << endl;

        ChatHandler chatHandler;
        chatHandler.socket = hClientSock;
        chatHandler.cliInfo = clientInfo;

        boost::thread hThread(boost::bind(&ChatHandler::chatHandler, &chatHandler, &activeUsers));
        threads.add_thread(&hThread);
    }
}
