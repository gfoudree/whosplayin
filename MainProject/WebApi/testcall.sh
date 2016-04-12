#!/bin/sh
URL="http://10.26.53.174:5000/"
CMDSTR="curl -H \"Content-Type: application/json\" -X POST -d"
if [ $1 == login ]; then
	FULLURL=$URL"user/authenticate"
	eval "$CMDSTR '{\"username\":\"grant\",\"password\":\"password\"}' $FULLURL"
elif [ $1 == newgame ]; then
	FULLURL=$URL"games/newGame"
	eval "$CMDSTR '{\"sessionId\":\"$2\",\"username\":\"grant\",\"gameTitle\":\"TestTitle\",\"gameTypeID\":\"1\",\"numPlayers\":\"5\",\"maxPlayers\":\"10\",\"dateCreated\":\"20160816\",\"startTime\":\"15:00:30.0000\",\"endTime\":\"15:13:30.0000\",\"captainID\":\"2\",\"zipcode\":\"50013\",\"altitude\":\"14.3\",\"latitude\":\"14.241919234\",\"longitude\":\"84.1234911\",\"state\":\"IA\",\"city\":\"Ames\"}' $FULLURL"

elif [ $1 == getgames ]; then
	FULLURL=$URL"games/getCurrentGames"
	eval "$CMDSTR '{\"sessionId\":\"$2\",\"username\":\"grant\"}' $FULLURL"

elif [ $1 == getfriends ]; then
	FULLURL=$URL"user/getFriendsList"
	eval "$CMDSTR '{\"username\":\"grant\",\"sessionId\":\"$2\",\"id\":\"20\"}' $FULLURL"

else
	eval $CMDSTR $1 $2
fi
