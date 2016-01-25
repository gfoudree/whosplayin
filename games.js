var db = require('./db');

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
  console.log('test');
  console.log(request.body.test);
  response.send(request.body.test);
}
