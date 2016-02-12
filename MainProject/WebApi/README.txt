Authenticate a user:
http://localhost:5000/user/authenticate?user=fred&password=password
-Returns a sessionID that needs to be sent to ALL other API queries if successful or invalid if not
-All the fields need to be specified

Register a user:
http://localhost:5000/user/create?username=tom&email=tom@aa.com&name=Tom%20Collin&age=20&gender=male&password=5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8&location=Chicago&phoneNumber=7732221144
-Returns OK if successful or invalid if not
-Password is a sha256 hash of the password
-All the fields need to be specified
