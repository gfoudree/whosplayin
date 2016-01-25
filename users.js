var crypto = require('crypto');
var db = require('./db');

function RNG(username)
{
  var randomString = crypto.randomBytes(64).toString('hex');
  db.setValueRedis('user:' + username, randomString); //Store their 'session-id' in redis. We need to check against this for each API call
  return randomString;
}

exports.userHandler = function(request, response)
{
  var id = request.query.id;
  var sessionID = request.query.sessionID;

  if (!id || sessionID.length === 0 || !sessionID || id < 1)
  {
    response.write('Invalid query');
  }
  else
  {
    db.sqlQuery('SELECT username FROM users WHERE ID=\'' + id + '\'', function(rows) //Get username
    {
      db.getValueRedis('user:' + rows[0]['username'], function(reply) //Get session ID from redis
      {
        if (reply == sessionID && reply != null) //Check if the passed sessionID == stored sessionID
        {
          console.log('Correct');
          db.sqlQuery('SELECT username,id,name,age,gender,location,raiting,verified,dateCreated,lastLogin,picture,gamesPlayed,gamesCreated FROM users WHERE ID=\'' + id + '\'', function(rows)
          {
            response.send(rows);
          });
        }
        else
        {
          console.log('Incorrect');
          response.send('Invalid Query');
        }
      });
    });
  }
}

exports.userAuthenticator = function(request, response)
{
  var password = request.query.password;
  var username = request.query.user;
  var hash = crypto.createHash('sha256');

  if (password.length < 1 || username.length < 1)
  {
    response.send('Invalid data');
  }
  db.sqlQuery('SELECT PASSWORD FROM users WHERE username = \'' + username + '\'', function(storedPassword)
  {
    hash.update(password);
    var hashedPw = hash.digest('hex');
    var loginStatus = {correct : 'false', sessionID : ''};
    if (hashedPw.toLowerCase() == storedPassword[0]['PASSWORD'].toLowerCase())
    {
      loginStatus.correct = 'true'; //Password is correct
      loginStatus.sessionID = RNG(username);
      response.json(loginStatus);
    }
    else {
      loginStatus.correct = 'false'; //Password is incorrect
      response.json(loginStatus);
    }
  });
}

exports.friendsList = function(request, response)
{

}
