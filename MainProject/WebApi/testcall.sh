#!/bin/sh
URL="http://localhost:5000/"
CMDSTR="curl -H \"Content-Type: application/json\" -X POST -d"
if [ $1 == login ]; then
	FULLURL=$URL"user/authenticate"
	eval "$CMDSTR '{\"username\":\"grantTheAnt\",\"password\":\"password\"}' $FULLURL"
elif [ $1 == newgame ]; then
	FULLURL=$URL"games/newGame"
	eval "$CMDSTR '{\"sessionId\":\"$2\",\"username\":\"grantTheAnt\",\"gameTitle\":\"TestTitle\",\"gameTypeID\":\"1\",\"numPlayers\":\"5\",\"maxPlayers\":\"10\",\"dateCreated\":\"20160816\",\"startTime\":\"15:00:30.0000\",\"endTime\":\"15:13:30.0000\",\"captainID\":\"2\",\"zipcode\":\"50013\",\"altitude\":\"14.3\",\"latitude\":\"14.241919234\",\"longitude\":\"84.1234911\",\"state\":\"IA\",\"city\":\"Ames\"}' $FULLURL"

elif [ $1 == getgames ]; then
	FULLURL=$URL"games/getCurrentGames"
	eval "$CMDSTR '{\"sessionId\":\"$2\",\"username\":\"grantTheAnt\"}' $FULLURL"

else
	eval $CMDSTR $1 $2
fi
