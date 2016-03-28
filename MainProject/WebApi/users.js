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
  if (!sessionId || !username || sessionId.length < 1 ||  username.length < 1)
  {
    done('Invalid query');
  }
  else
  {
      db.getValueRedis('user:' + username, function(reply) //Get session ID from redis
      {
        if (reply == sessionId && reply != null) //Check if the passed sessionId == stored sessionId
        {
          if (sqlStmt && sqlStmt.length > 0)
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
  var id = request.body.id;
  var sessionId = request.body.sessionId;
  var username = request.body.username;

  validateUser(sessionId, username, 'SELECT username,id,name,age,gender,location,rating,verified,dateCreated,lastLogin,picture,gamesPlayed,gamesCreated FROM users WHERE ID=\'' + id + '\'', function(data)
  {
    response.send(data); //Send user info in JSON
  });
}

var authenticate = function(request, response)
{
  console.log(request);
  var password = request.body.password;
  var username = request.body.username;
  var hash = crypto.createHash('sha256');
  hash.update(password);
  console.log(password);
  if (!password || !username || password.length < 1 || username.length < 1)
  {
    response.send('Invalid data');
  }
  db.sqlQuery('SELECT USR_password FROM User WHERE USR_username = \'' + username + '\'', function(storedPassword) //Check if user exists
  {

    var hashedPw = hash.digest('hex');
    var loginStatus = {correct : 'false', sessionId : ''};
    if (hashedPw.toLowerCase() == storedPassword[0]['USR_password'].toLowerCase())
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
  var id = request.body.id;
  var sessionId = request.body.sessionId;
  var username = request.body.username;

  validateUser(sessionId, username, 'CALL `db309grp12`.`stp_sel_userFriends`(' + id + ');', function(data) //Call stored proceedure to get friends list
  {
    response.send(data);
  });
}

var create = function(request, response) //Create a user
{
  var username = request.body.username;
  var email = request.body.email;
  var name = request.body.name;
  var age = request.body.age;
  var gender = request.body.gender;
  var password = request.body.password;
  var location = request.body.location;
  var phoneNumber = request.body.phoneNumber;

  if (!username || !email || !name || !age || !gender || !password || !location || !phoneNumber || username.length === 0 || email.length === 0 || name.length === 0 ||
    gender.length === 0 || password.length === 0 || location.length === 0 || phoneNumber.length === 0 || age < 1)
    {
      response.send("Invalid!");
    }
    else{
      var query = "INSERT INTO User (username, email, name, age, gender, password, location, dateCreated, phoneNumber) VALUES (\'" + username + "\',\'" + email + "\',\'" + name + "\',\'" + age+ "\',\'" +gender+ "\',\'" +password+ "\',\'" +location + "\',NOW(),\'" +  phoneNumber + "\')";
      db.sqlQuery(query, function()
      {
        response.send("OK");
      });
    }
}

var status = function(request, response)
{
  var username = request.body.username;
  var sessionId = request.body.sessionId;

  if (!sessionId || !username || sessionId.length === 0 || username.length === 0)
  {
    response.send("False");
  }
  else {
    db.getValueRedis("user:" + username, function (value)
    {
      if (value == sessionId)
      {
        response.send("True");
      }
      else {
        response.send("False");
      }
    });
  }
}

var getId = function(request, response)
{
  var sessionId = request.body.sessionId;
  var username = request.body.username;
  var user = request.body.user;
  console.log("Test");
  validateUser(sessionId, username, 'SELECT id FROM User WHERE username=\'' + user + '\'', function (done)
  {
    response.send(done);
  });
}

module.exports = {
  create: create,
  getFriendsList: getFriendsList,
  authenticate: authenticate,
  getInfo: getInfo,
  validateUser: validateUser,
  RNG: RNG,
  status: status,
  getId: getId
}
