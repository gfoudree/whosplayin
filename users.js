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

  if (id > 0)
  {
    db.sqlQuery('SELECT id,username FROM user WHERE ID=\'' + id + '\'', function(rows)
    {
      console.log('user:' + rows[0].username);
      db.getValueRedis('user:' + rows[0].username, function(reply)
      {
        if (reply == sessionID)
        {
          response.send(rows);
        }
        else {
          response.send('Invalid session ID!');
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
  db.sqlQuery('SELECT PASSWORD FROM user WHERE username = \'' + username + '\'', function(storedPassword)
  {
    hash.update(password);
    var hashedPw = hash.digest('hex');
    var loginStatus = {correct : 'false', sessionID : ''};
    if (hashedPw == storedPassword[0]['PASSWORD'])
    {
      loginStatus.correct = 'true'; //Password is correct
      loginStatus.sessionID = RNG(username);
      response.json(loginStatus);
    }
    else {
      loginStatus.correct = 'false'; //Passowrd is incorrect
      response.json(loginStatus);
    }
  });
}
