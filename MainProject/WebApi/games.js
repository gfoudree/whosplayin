var db = require('./db');
var users = require('./users');

//TODO: make this work with REDIS to store current games instead of using mysql
var getGames = function(request, response)
{
  var sessionId = request.body.sessionId;
  var username = request.body.username;

  var query = "CALL db309grp12.stp_GetCurrentGames();";

<<<<<<< HEAD
  if (sessionId && username)
  {
    console.log("Gettin current games");
    users.validateUser(sessionId, username, query, function(reply)
    {
      if (reply == 'Error retrieving SQL data')
        response.send('Invalid');
      else {
        response.send(reply);
      }
    });
  }
  else {
    response.send("Invalid");
  }
=======
  users.validateUser(sessionId, username, query, function(reply)
  {
    if (reply == 'Error retrieving SQL data')
      response.send('Invalid');
    else {
      //Maybe set a redis key with TTL that expires that contains the list of current games We need to get gameID somehow
      response.send(reply);
    }
  });
>>>>>>> parent of 3f38c9a... Merge branch 'rick_user_profile_view'
}

var newGame = function(request, response)
{
  var gameTitle = request.body.gameTitle;
  var gameTypeID = request.body.gameTypeID;
  var numPlayers = request.body.numPlayers;
  var maxPlayers = request.body.maxPlayers;
  var dateCreated = request.body.dateCreated;
  var startTime = request.body.startTime;
  var endTime = request.body.endTime;
  var captainID = request.body.captainID;
  var zipcode = request.body.zipcode;
  var altitude = request.body.altitude;
  var latitude = request.body.latitude;
  var longitude = request.body.longitude;
  var state = request.body.state;
  var city = request.body.city;
  var sessionId = request.body.sessionId;
  var username = request.body.username;

<<<<<<< HEAD
  if (gameTitle && gameTypeID && numPlayers && maxPlayers && dateCreated && startTime && endTime && captainID && zipcode && altitude && latitude && longitude && state && city && sessionId && username)
  {
  var query = "CALL db309grp12.stp_CreateGame (\'" + gameTitle + "\',\'" + gameTypeID+ "\',\'" +  numPlayers+ "\',\'" +  maxPlayers+ "\',\'" +  dateCreated + "\',\'" +  startTime+ "\',\'" +  endTime+ "\',\'" +  captainID+ "\',\'" +  zipcode + "\',\'" +  altitude + "\',\'" +  latitude+ "\',\'" +  longitude + "\',\'" + state + "\',\'" + city + "\');";
=======
    var query = "CALL db309grp12.stp_CreateGame (\'" + gameTitle + "\',\'" + gameTypeID+ "\',\'" +  numPlayers+ "\',\'" +  maxPlayers+ "\',\'" +  dateCreated + "\',\'" +  startTime+ "\',\'" +  endTime+ "\',\'" +  captainID+ "\',\'" +  zipcode + "\',\'" +  altitude + "\',\'" +  latitude+ "\',\'" +  longitude + "\',\'" + state + "\',\'" + city + "\');";

    console.log(query);

>>>>>>> parent of 3f38c9a... Merge branch 'rick_user_profile_view'
    users.validateUser(sessionId, username, query, function(reply) //Validate user before we do anything
    {
      if (reply == 'Error retrieving SQL data')
        response.send('Invalid');
      else {
        //Maybe set a redis key with TTL that expires that contains the list of current games We need to get gameID somehow
        response.send('Success');
      }
    });

}

var addPlayer = function(request, response)
{
  var gameId = request.body.gameId;
  var userId = request.body.userId;
  var sessionId = request.body.sessionId;
  var username = request.body.username;

<<<<<<< HEAD
  if (!gameId || gameId < 1 || !userId || userId < 1 || !sessionId || sessionId.length === 0 || !username || username.length === 0)
=======
  if (!gameId || gameId < 1 || !playerId || playerId < 1 || !sessionId || sessionId.length === 0 || !username || username.length === 0)
>>>>>>> parent of 3f38c9a... Merge branch 'rick_user_profile_view'
  {
    response.send('Invalid');
  }
  else {
<<<<<<< HEAD
    var query = "CALL db309grp12.stp_AddUserToGame(\'" + userId + "\',\'" + gameId + "\');";
    users.validateUser(sessionId, username, query, function(result)
=======
    users.validateUser(sessionId, username, '', function(result)
>>>>>>> parent of 3f38c9a... Merge branch 'rick_user_profile_view'
    {
      if (result === 'Valid')
      {
        db.listAddRedis('game:' + gameId, playerId, function(reply) //Adding player to game:gameId key in redis.
        {
          if (reply == 0)
          {
            response.send('Success');
          }
          else {
            response.send('Failure');
          }
        });
      }
      else {
        response.send('Success');
      }
    });
  }
}

var getPlayers = function(request, response)
{
  var gameId = request.body.gameId;
  var sessionId = request.body.sessionId;
  var username = request.body.username;

  if (!gameId || gameId < 1 || !sessionId || !username || username.length === 0)
  {
    response.send('Invalid');
  }
  else {
    users.validateUser(sessionId, username, '', function(result)
    {
      if (result === 'Valid')
      {
        db.listGetRedis('game:' + gameId, function(reply) //Get players in redis key
        {
          response.send(reply);
        });
      }
      else {
        response.send(result);
      }
    });
  }
}

var removeUserFromGame = function(request, response)
{
  var gameId = request.body.gameId;
  var sessionId = request.body.sessionId;
  var username = request.body.username;
  var userId = request.body.userId;

  if (userId && username && sessionId && gameId)
  {
    var query = "CALL db309grp12.stp_DeleteUserFromGame(\'" + userId + "\',\'" + gameId + "\');";
    users.validateUser(sessionId, username, query, function (result)
    {
      if (result == 'Error retrieving SQL data')
        response.send('Invalid');
      else {
        response.send('Success')
      }
    });
  }
  else {
    response.send('Invalid');
  }
}

module.exports = {
  newGame: newGame,
  getPlayers: getPlayers,
  addPlayer: addPlayer,
  getGames: getGames,
  removeUserFromGame: removeUserFromGame
}
