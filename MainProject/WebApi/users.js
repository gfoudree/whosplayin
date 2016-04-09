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
  console.log(sqlStmt);
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

  if (!id || !sessionId || !username)
    response.send('Invalid');
  else {
    validateUser(sessionId, username, 'CALL db309grp12.stp_GetFriendsList(' + id + ');', function(data) //Call stored proceedure to get friends list
    {
      if (data == 'Error retrieving SQL data')
        response.send('Invalid');
      else
        response.send(data);
    });
  }

}

var create = function(request, response) //Create a user
{
  var username = request.body.username;
  var email = request.body.email;
  var name = request.body.name;
  var age = request.body.age;
  var gender = request.body.gender;
  var password = request.body.password;
  var phone = request.body.phone;

  if (!username || !email || !name || !age || !gender || !password || !phone)
    {
      response.send("Invalid");
    }
    else{
      var query = "CALL db309grp12.CreateUser(\'" + username + "\',\'" + password + "\',\'" + name + "\',\'" + age+ "\',\'" +gender+ "\',\'" +email+  + "\',\'" + phone + "\',\'\');";
      console.log(query);
      db.sqlQuery(query, function(res)
      {
        if (res == "Error retrieving SQL data")
          response.send("Invalid");
        else
          response.send("Success");
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
<<<<<<< HEAD

  if (user && username && sessionId)
  {
    var query = 'SELECT USR_id FROM db309grp12.User WHERE USR_username=\'' + user + '\';';
    validateUser(sessionId, username, query, function (done)
    {
      response.send(done);
    });
  }
  else
  {
    response.send("Invalid");
  }
}

var addFriend = function (request, response)
{
  var sessionId = request.body.sessionId;
  var username = request.body.username;
  var userId = request.body.userId;
  var friendId = request.body.friendId;

  if (user && username && sessionId && friendId)
=======
  console.log("Test");
  validateUser(sessionId, username, 'SELECT id FROM User WHERE username=\'' + user + '\'', function (done)
>>>>>>> parent of 3f38c9a... Merge branch 'rick_user_profile_view'
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
