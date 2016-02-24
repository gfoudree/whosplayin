var db = require('./db');
var users = require('./users');

//TODO: make this work with REDIS to store current games instead of using mysql
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
  var title = request.body.title;
  var maxPlayers = request.body.maxPlayers;
  var startTime = request.body.startTime;
  var endTime = request.body.endTime;
  var gameType = request.body.gameType;
  var sessionId = request.body.sessionId;
  var username = request.body.username;
  var captainId = request.body.captainId;

  if (!title || !maxPlayers || !startTime || !endTime || !gameType || !sessionId || !username || !captainId)
  {
    response.send('Invalid');
  }
  else {
    var query = 'INSERT INTO games (title,numPlayers,maxPlayers,startTime,endTime,gameType,captainId) VALUES (\'' + title + '\',\'0\',\'' + maxPlayers + '\',\'' + startTime + '\', \
      \'' + endTime + '\',\'' + gameType + '\',\'' + captainId +'\')';

      console.log(query);
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
}

var addPlayer = function(request, response)
{
  var gameId = request.body.gameId;
  var playerId = request.body.playerId;
  var sessionId = request.body.sessionId;
  var username = request.body.username;

  if (!gameId || gameId < 1 || !playerId || playerId < 1 || !sessionId || sessionId.length === 0 || !username || username.length === 0)
  {
    response.send('Invalid');
  }
  else {
    users.validateUser(sessionId, username, '', function(result)
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
        response.send('Invalid');
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
