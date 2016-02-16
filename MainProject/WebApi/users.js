var crypto = require('crypto');
var db = require('./db');

function RNG(username)
{
  var randomString = crypto.randomBytes(64).toString('hex');
  db.setValueRedis('user:' + username, randomString); //Store their 'session-id' in redis. We need to check against this for each API call
  return randomString;
}

//Returns invalid if the user is not authenticated, and returns valid if authenticated
var validateUser = function(sessionId, username, sqlStmt, done)
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

var getInfo = function(request, response)
{
  var id = request.query.id;
  var sessionId = request.query.sessionId;
  var username = request.query.username;

  validateUser(sessionId, username, 'SELECT username,id,name,age,gender,location,raiting,verified,dateCreated,lastLogin,picture,gamesPlayed,gamesCreated FROM users WHERE ID=\'' + id + '\'', function(data)
  {
    response.send(data);
  });
}

var authenticator = function(request, response)
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

var getFriendsList = function(request, response)
{
  var id = request.query.id;
  var sessionId = request.query.sessionId;
  var username = request.query.username;

  users.validateUser(sessionId, username, 'CALL `db309grp12`.`stp_sel_userFriends`(' + id + ');', function(data)
  {
    response.send(data);
  });
}

var create = function(request, response)
{
  var username = request.query.username;
  var email = request.query.email;
  var name = request.query.name;
  var age = request.query.age;
  var gender = request.query.gender;
  var password = request.query.password;
  var location = request.query.location;
  var phoneNumber = request.query.phoneNumber;

  if (!username || !email || !name || !age || !gender || !password || !location || !phoneNumber || username.length === 0 || email.length === 0 || name.length === 0 ||
    gender.length === 0 || password.length === 0 || location.length === 0 || phoneNumber.length === 0 || age < 1)
    {
      response.send("Invalid!");
    }
    else{
      var query = "INSERT INTO users (username, email, name, age, gender, password, location, dateCreated, phoneNumber) VALUES (\'" + username + "\',\'" + email + "\',\'" + name + "\',\'" + age+ "\',\'" +gender+ "\',\'" +password+ "\',\'" +location + "\',NOW(),\'" +  phoneNumber + "\')";
      db.sqlQuery(query, function()
      {
        response.send("OK");
      });
    }
}
module.exports = {
  create: create,
  getFriendsList: getFriendsList,
  authenticator: authenticator,
  getInfo: getInfo,
  validateUser: validateUser,
  RNG: RNG
}
