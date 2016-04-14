var db = require('./db');
var users = require('./users');

var getGames = function(request, response)
{
  var sessionId = request.body.sessionId;
  var username = request.body.username;

  var query = "CALL db309grp12.stp_GetCurrentGames();";

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

  if (gameTitle && gameTypeID && numPlayers && maxPlayers && dateCreated && startTime && endTime && captainID && zipcode && altitude && latitude && longitude && state && city && sessionId && username)
  {
  var query = "CALL db309grp12.stp_CreateGame (\'" + gameTitle + "\',\'" + gameTypeID+ "\',\'" +  numPlayers+ "\',\'" +  maxPlayers+ "\',\'" +  dateCreated + "\',\'" +  startTime+ "\',\'" +  endTime+ "\',\'" +  captainID+ "\',\'" +  zipcode + "\',\'" +  altitude + "\',\'" +  latitude+ "\',\'" +  longitude + "\',\'" + state + "\',\'" + city + "\');";
    users.validateUser(sessionId, username, query, function(reply) //Validate user before we do anything
    {
      if (reply == 'Error retrieving SQL data')
        response.send('Invalid');
      else {
        response.send('Success');
      }
    });
  }
  else {
    response.send('Invalid');
  }
}

var addPlayer = function(request, response)
{
  var gameId = request.body.gameId;
  var userId = request.body.userId;
  var sessionId = request.body.sessionId;
  var username = request.body.username;

  if (!gameId || gameId < 1 || !userId || userId < 1 || !sessionId || sessionId.length === 0 || !username || username.length === 0)
  {
    response.send('Invalid');
  }
  else {
    var query = "CALL db309grp12.stp_AddUserToGame(\'" + userId + "\',\'" + gameId + "\');";
    users.validateUser(sessionId, username, query, function(result)
    {
      if (result == 'Error retrieving SQL data')
      {
        response.send('Invalid');
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
    var query = "CALL db309grp12.stp_GetUsersInGame(" + gameId + ")";
    users.validateUser(sessionId, username, query, function(result)
    {
      if (result == 'Error retrieving SQL data')
      {
        response.send('Invalid');
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
