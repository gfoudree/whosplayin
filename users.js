var crypto = require('crypto');
var db = require('./db.js');

nonceList = [];

function generateNonce()
{
  var randomStr = crypto.randomBytes(64).toString('hex');
  nonceList.push(randomStr); //Add this nonce to the list of nonces we will accept
  if (nonceList.length > 255) //Reset list of nonces incase of brute force attack
  {
    nonceList = [];
  }
  return randomStr;
}

exports.userHandler = function(request, response)
{
  response.send('hi');
}

exports.getNonce = function(request, response)
{
  response.send(generateNonce());
}

exports.userAuthenticator = function(request, response)
{
  var nonce = request.param('nonce');
  var password = request.param('password');
  var username = request.param('user');

  //if (nonceList.indexOf(nonce) != -1) //This nonce is in the list so we can go ahead and use it
  //{
    response.send('OK this is valid');
    nonceList.splice(nonceList.indexOf(nonce), 1); //Delete this nonce from the list

    var hash = crypto.createHash('sha256');
    db.sqlQuery('SELECT PASSWORD FROM user WHERE username = \'' + username + '\'', function(data)
    {
      hash.update(data + nonce);
      var hashedPw = hash.digest('hex');

      console.log(nonce);
      console.log(data);
      console.log(hashedPw);
    });
  //}
  //else { //Invalid nonce
  //  response.send('Invalid!');
  //}
}
