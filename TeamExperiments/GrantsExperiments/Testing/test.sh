openssl s_client -connect localhost:9090 -tls1_2 -cert client_certificates/cert.pem -key client_certificates/key.pem -CAfile cacert.pem -state -no_ssl3 -no_ssl2
