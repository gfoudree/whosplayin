//
// Created by gfoudree on 2/3/16.
//

#include "ChatServer.h"
#include "ChatHandler.h"

ChatServer::~ChatServer() {
    threads.join_all();
    std::for_each(chatHandlers.begin(), chatHandlers.end(), [](ChatHandler *ch)
    {
        delete ch;
    });

    SSL_CTX_free(ctx);
    ERR_free_strings();
    EVP_cleanup();
    EC_KEY_free(ecdh);
    close(serverSock);
}

ChatServer::ChatServer(int port, const char *cacert, const char *cert, const char *key) {
    OpenSSL_add_all_algorithms();
    SSL_load_error_strings();
    ERR_load_BIO_strings();
    SSL_library_init();

    method = TLSv1_2_server_method();
    ctx = SSL_CTX_new(method);

    if (ctx == NULL)
    {
        ERR_print_errors_fp(stderr);
        throw "Error creating new SSL CTX";
    }

    SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER, NULL);
    if (SSL_CTX_load_verify_locations(ctx, cacert, NULL) != 1) {
        ERR_print_errors_fp(stderr);
        throw "Error loading CA certificate";
    }

    SSL_CTX_set_verify_depth(ctx, 4);

    SSL_CTX_set_cipher_list(ctx, "ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES128-GCM-SHA256");

    ecdh = EC_KEY_new_by_curve_name(NID_X9_62_prime256v1);
    if (!ecdh) {
        ERR_print_errors_fp(stderr);
        throw "Error creating new EC curve";
    }
    if (SSL_CTX_set_tmp_ecdh(ctx, ecdh) != 1) {
        ERR_print_errors_fp(stderr);
        throw "Error setting ECDH params";
    }

    if (SSL_CTX_use_certificate_file(ctx, cert, SSL_FILETYPE_PEM) <= 0) {
        ERR_print_errors_fp(stderr);
        throw "Error using certificate file!";
    }
    if (SSL_CTX_use_PrivateKey_file(ctx, key, SSL_FILETYPE_PEM) <= 0) {
        ERR_print_errors_fp(stderr);
        throw "Error using private key file!";
    }
    if (!SSL_CTX_check_private_key(ctx)) {
        ERR_print_errors_fp(stderr);
        throw "Server private key error!";
    }

    memset(&sockInfo, 0, sizeof(sockaddr_in));

    sockInfo.sin_port = htons(port);
    sockInfo.sin_addr.s_addr = htonl(INADDR_ANY);
    sockInfo.sin_family = (AF_INET);
}

void ChatServer::init() {
    serverSock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (serverSock < 0) {
        close(serverSock);
        throw "Error creating socket";
    }

    if (bind(serverSock, (struct sockaddr *)&sockInfo, sizeof(struct sockaddr_in)) < 0) {
        close(serverSock);
        throw "Error binding";
    }

    if (listen(serverSock, 10) < 0) {
        close(serverSock);
        throw "Error listening";
    }

    while (1)
    {
        SSL *ssl;
        int hClientSock = 0;
        sockaddr_in clientInfo;
        socklen_t cliLen;

        cliLen = sizeof(clientInfo);
        memset(&clientInfo, 0, sizeof(sockaddr_in));

        hClientSock = accept(serverSock, (struct sockaddr*)&clientInfo, &cliLen);

        if (hClientSock < 0)
        {
            cerr << "Error on accept\n";
            close(hClientSock);
        }

        ssl = SSL_new(ctx);
        if (ssl == NULL){
            ERR_print_errors_fp(stderr);
            throw "Error creating new SSL";
        }

        if (SSL_set_fd(ssl, hClientSock) != 1)
        {
            ERR_print_errors_fp(stderr);
            throw "Error creating SSL connection";
        }
        int r = SSL_accept(ssl);
        if (r != 1)
        {
            ERR_print_errors_fp(stderr);
            throw std::to_string(SSL_get_error(ssl, r)).c_str();
        }

        cout << "Got connection from: " << inet_ntoa(clientInfo.sin_addr) << " Using cipher " << SSL_get_cipher(ssl) << endl;
        ChatHandler *chatHandler = new ChatHandler(hClientSock, clientInfo, ssl);

        boost::thread hThread(boost::bind(&ChatHandler::chatHandler, chatHandler, &activeUsers));
        threads.add_thread(&hThread);
        chatHandlers.push_back(chatHandler);
    }
}
