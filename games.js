var db = require('./db');
var users = require('./users');

exports.getGames = function(request, response)
{
  db.getValueRedis('test', function(value)
  {
    response.send(value);
  });
  db.setValueRedis('test', 'testData');
}

exports.newGame = function(request, response)
{

}

exports.addPlayer = function(request, response)
{
  var gameId = request.query.gameId;
  var playerId = request.query.playerId;
  var sessionId = request.query.sessionId;
  var username = request.query.username;

  if (!gameId || gameId < 1 || !playerId || playerId < 1 || !sessionId || !username || username.length === 0)
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

exports.getPlayers = function(request, response)
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
