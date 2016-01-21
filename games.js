var db = require('./db');

exports.getGames = function(request, response)
{
  db.getValueRedis('test', function(value)
  {
    response.send(value);
  });
  db.setValueRedis('test', 'testData');
}
