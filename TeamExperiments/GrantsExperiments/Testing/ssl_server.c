#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/x509v3.h>
#include <openssl/bn.h>
#include <openssl/asn1.h>
#include <openssl/x509.h>
#include <openssl/x509_vfy.h>
#include <openssl/pem.h>
#include <stdio.h>
#include <sys/types.h>   // Types used in sys/socket.h and netinet/in.h
#include <netinet/in.h>  // Internet domain address structures and functions
#include <sys/socket.h>  // Structures and functions used for socket API
#include <netdb.h>
#include <arpa/inet.h>

int main()
{
	OpenSSL_add_all_algorithms();
	SSL_load_error_strings();
	ERR_load_BIO_strings();
	SSL_library_init();
	
	SSL *ssl;
	SSL_CTX *ctx;
	SSL_METHOD *method;
	int sock;
	struct sockaddr_in server;
	char buf[128];

	method = TLSv1_2_server_method();

	memset(buf, 0, sizeof(buf));
	memset(&server, 0, sizeof(struct sockaddr_in));
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = htonl(INADDR_ANY);
	server.sin_port = htons(9090);

	ctx = SSL_CTX_new(method);

	SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER, NULL);
	SSL_CTX_load_verify_locations(ctx, "cacert.pem", NULL);
	SSL_CTX_set_verify_depth(ctx, 1);

	SSL_CTX_set_cipher_list(ctx, "ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES128-GCM-SHA256");

	EC_KEY *ecdh = EC_KEY_new_by_curve_name(NID_X9_62_prime256v1);
	SSL_CTX_set_tmp_ecdh(ctx, ecdh);

	if (SSL_CTX_use_certificate_file(ctx, "cert.pem", SSL_FILETYPE_PEM) <= 0)
	{
		printf("Certificate Error\n");
		exit(-1);
	}
	if (SSL_CTX_use_PrivateKey_file(ctx, "key.pem", SSL_FILETYPE_PEM) <= 0)
	{
		printf("Private Key Error\n");
		exit(-1);
	}

	if (!SSL_CTX_check_private_key(ctx))
	{
		printf("Cert check error\n");
		exit(-1);
	}

	sock = socket(AF_INET, SOCK_STREAM, 0);
	bind(sock, (struct sockaddr *)&server, sizeof(struct sockaddr_in));
        listen(sock, 10);

	int cli;
	struct sockaddr_in clientInfo;
	socklen_t cliLen;
	
	cliLen = sizeof(clientInfo);
	memset(&clientInfo, 0, sizeof(struct sockaddr_in));

	cli = accept(sock, (struct sockaddr*)&clientInfo, &cliLen);

	close(sock);

	ssl = SSL_new(ctx);
	SSL_set_fd(ssl, cli);
	SSL_accept(ssl);
	
	printf("Got a connection using cipher: %s\n", SSL_get_cipher(ssl));

	X509 *cliCert;

	cliCert = SSL_get_peer_certificate(ssl);
	if (cliCert == NULL)
	{
		printf("Client did not supply certificate\n");
	}
	else
	{
		printf("Subject: %s\nIssuer: %s\n", X509_NAME_oneline(X509_get_subject_name(cliCert), 0, 0), X509_NAME_oneline(X509_get_issuer_name(cliCert), 0, 0));
		X509_free(cliCert);
	}
	SSL_write(ssl, "Test", 4);

	SSL_read(ssl, buf, sizeof(buf)-1);
	SSL_write(ssl, "Hello!", 7);
	
	printf("%s", buf);

	SSL_shutdown(ssl);
	SSL_free(ssl);
	SSL_CTX_free(ctx);
	ERR_free_strings();
	EVP_cleanup();
	close(cli);
	EC_KEY_free(ecdh);
}


