var crypto = require('crypto');
var db = require('./db');

function RNG(username)
{
  var randomString = crypto.randomBytes(64).toString('hex');
  db.setValueRedis('user:' + username, randomString); //Store their 'session-id' in redis. We need to check against this for each API call
  return randomString;
}

//Returns invalid if the user is not authenticated, and returns valid if authenticated
function validateUser(sessionId, username, sqlStmt, done)
{
  if (sessionId.length === 0 || !sessionId || !username || username.length === 0)
  {
    done('Invalid query');
  }
  else
  {
      db.getValueRedis('user:' + username, function(reply) //Get session ID from redis
      {
        if (reply == sessionId && reply != null) //Check if the passed sessionId == stored sessionId
        {
          if (sqlStmt.length > 0 && sqlStmt)
          {
            db.sqlQuery(sqlStmt, function(rows)
            {
              done(rows);
            });
          }
          else {
            done('Valid');
          }
        }
        else
        {
          done('Invalid');
        }
      });
  }
}

exports.getInfo = function(request, response)
{
  var id = request.query.id;
  var sessionId = request.query.sessionId;
  var username = request.query.username;
  validateUser(sessionId, username, 'SELECT username,id,name,age,gender,location,raiting,verified,dateCreated,lastLogin,picture,gamesPlayed,gamesCreated FROM users WHERE ID=\'' + id + '\'', function(data)
  {
    response.send(data);
  });
}

exports.authenticator = function(request, response)
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
    var loginStatus = {correct : 'false', sessionId : ''};
    if (hashedPw.toLowerCase() == storedPassword[0]['PASSWORD'].toLowerCase())
    {
      loginStatus.correct = 'true'; //Password is correct
      loginStatus.sessionId = RNG(username);
      response.json(loginStatus);
    }
    else {
      loginStatus.correct = 'false'; //Password is incorrect
      response.json(loginStatus);
    }
  });
}

exports.getFriendsList = function(request, response)
{
  var id = request.query.id;
  var sessionId = request.query.sessionId;
  var username = request.query.username;

  validateUser(sessionId, username, 'CALL `sql5103427`.`stp_sel_userFriends`(' + id + ');', function(data)
  {
    response.send(data);
  });
}
