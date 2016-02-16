var db = require('./db');
var users = require('./users');

var getGames = function(request, response)
{
  db.getValueRedis('test', function(value)
  {
    response.send(value);
  });
  db.setValueRedis('test', 'testData');
}

var newGame = function(request, response)
{
  var title = request.query.title;
  var maxPlayers = request.query.maxPlayers;
  var startTime = request.query.startTime;
  var endTime = request.query.endTime;
  var gameType = request.query.gameType;
  var sessionId = request.query.sessionId;
  var username = request.query.username;
  var captainId = request.query.captainId;

  if (!title || !maxPlayers || !startTime || !endTime || !gameType || !sessionId || !username || !captainId)
  {
    response.send('Invalid');
  }
  else {
    var query = 'INSERT INTO games (title,numPlayers,maxPlayers,startTime,endTime,gameType,captainId) VALUES (\'' + title + '\',\'0\',\'' + maxPlayers + '\',\'' + startTime + '\', \
      \'' + endTime + '\',\'' + gameType + '\',\'' + captainId +'\')';

      console.log(query);
    users.validateUser(sessionId, username, query, function(reply)
    {
      if (reply == 'Error retrieving SQL data')
        response.send('Invalid');
      else {
        //Maybe set a redis key with TTL that expires that contains the list of current games
        response.send('Success');
      }
    });
  }
}

var addPlayer = function(request, response)
{
  var gameId = request.query.gameId;
  var playerId = request.query.playerId;
  var sessionId = request.query.sessionId;
  var username = request.query.username;

  if (!gameId || gameId < 1 || !playerId || playerId < 1 || !sessionId || sessionId.length === 0 || !username || username.length === 0)
  {
    response.send('Invalid');
  }
  else {
    users.validateUser(sessionId, username, '', function(result)
    {
      if (result === 'Valid')
      {
        db.listAddRedis('game:' + gameId, playerId, function(reply)
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
        response.send('Invalid');
      }
    });
  }
}

var getPlayers = function(request, response)
{
  var gameId = request.query.gameId;
  var sessionId = request.query.sessionId;
  var username = request.query.username;

  if (!gameId || gameId < 1 || !sessionId || !username || username.length === 0)
  {
    response.send('Invalid');
  }
  else {
    users.validateUser(sessionId, username, '', function(result)
    {
      if (result === 'Valid')
      {
        db.listGetRedis('game:' + gameId, function(reply)
        {
          response.send(reply);
        });
      }
      else {
        response.send('Invalid');
      }
    });
  }
}

module.exports = {
  newGame: newGame,
  getPlayers: getPlayers,
  addPlayer: addPlayer,
  getGames: getGames
}
