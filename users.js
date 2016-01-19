var crypto = require('crypto');
var db = require('./db');

exports.userHandler = function(request, response)
{
  response.send('User information here');
}

exports.userAuthenticator = function(request, response)
{
  var password = request.param('password');
  var username = request.param('user');
  var hash = crypto.createHash('sha256');

  if (password.length < 1 || username.length < 1)
  {
    response.send('Invalid data');
  }
  db.sqlQuery('SELECT PASSWORD FROM user WHERE username = \'' + username + '\'', function(storedPassword)
  {
    hash.update(password);
    var hashedPw = hash.digest('hex');
    var loginStatus = {correct : 'false'};
    if (hashedPw == storedPassword[0]['PASSWORD'])
    {
      loginStatus.correct = 'true'; //Password is correct
      response.json(loginStatus);
    }
    else {
      loginStatus.correct = 'false'; //Passowrd is incorrect
      response.json(loginStatus);
    }
  });
}
