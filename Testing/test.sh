openssl s_client -connect localhost:9090 -tls1_2 -cert cert.pem -key key.pem -CAfile cacert.pem -state -no_ssl3 -no_ssl2
