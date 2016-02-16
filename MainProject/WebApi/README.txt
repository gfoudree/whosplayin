Authenticate a user:
http://localhost:5000/user/authenticate?user=fred&password=password
-Returns a sessionID that needs to be sent to ALL other API queries if successful or invalid if not
-All the fields need to be specified

Register a user:
http://localhost:5000/user/create?username=tom&email=tom@aa.com&name=Tom%20Collin&age=20&gender=male&password=5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8&location=Chicago&phoneNumber=7732221144
-Returns OK if successful or invalid if not
-Password is a sha256 hash of the password
-All the fields need to be specified

Create a game:
http://localhost:5000/games/newGame?title=Test%20Game&maxPlayers=5&startTime=2016-02-16%2017:00:00&endTime=2016-02-16%2018:00:00&gameType=soccer&captainId=1&username=tom&sessionId=704cfeb7df0e3ff446125a73f856ecacaf4df1afb8d980f1af6b181686c233f8e7cea31994b749c56cc6c0f2df8b04f33f4d6f80f1c6d62f06c9b3436cefdd7f
