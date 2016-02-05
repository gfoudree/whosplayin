#include <iostream>
#include "ChatServer.h"

using namespace std;

int main() {
    try {
        ChatServer chatServer(8082);
        chatServer.init();
    }
    catch (const char* err)
    {
        cerr << err;
        return -1;
    }
    return 0;
}