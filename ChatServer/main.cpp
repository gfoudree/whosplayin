#include <iostream>
#include <sys/types.h>   // Types used in sys/socket.h and netinet/in.h
#include <netinet/in.h>  // Internet domain address structures and functions
#include <sys/socket.h>  // Structures and functions used for socket API
#include <netdb.h>       // Used for domain/DNS hostname lookup
#include <unistd.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

using namespace std;

int main() {
    struct sockaddr_in sockInfo;
    int hSock = 0, hClientSock = 0;
    memset(&sockInfo, 0, sizeof(sockaddr_in));

    sockInfo.sin_port = htons(8080);
    sockInfo.sin_addr.s_addr = htonl(INADDR_ANY);
    sockInfo.sin_family = (AF_INET);

    hSock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (hSock < 0) {
        cerr << "Error creating socket\n";
        close(hSock);
    }

    if (bind(hSock, (const sockaddr *)&sockInfo, sizeof(sockInfo)) < 0)
    {
        cerr << "Error binding\n";
        close(hSock);
    }

    if (listen(hSock, 5) < 0)
    {
        cerr << "Error listening\n";
        close(hSock);
    }

    while (1)
    {
        int bytesRead = 0;
        char buf[256] = {0};
        struct socklen_t clientInfo;

        memset(&clientInfo, 0, sizeof(socklen_t));


        hClientSock = accept(hSock, &clientInfo, sizeof(socklen_t));

        cout << "Got connection from: ";
        bytesRead = recv(hClientSock, buf, sizeof(buf), NULL);
        cout << buf;
        close(hSock);
        close(hClientSock);
        break;
    }
    return 0;
}