#include <iostream>
#include "ChatServer.h"

using namespace std;

int main() {
    try {
        ChatServer chatServer(9090, "/home/gfoudree/Documents/G12_WhosPlayin/TeamExperiments/GrantsExperiments/Testing/cacert.pem", "/home/gfoudree/Documents/G12_WhosPlayin/TeamExperiments/GrantsExperiments/Testing/cert.pem", "/home/gfoudree/Documents/G12_WhosPlayin/TeamExperiments/GrantsExperiments/Testing/key.pem");
        chatServer.init();
    }
    catch (const char* err)
    {
        cerr << err << endl;
        return -1;
    }
    return 0;
}