var redis = require('redis');

exports.getGames = function(request, response)
{
  getValue('test', function(value)
  {
    response.send(value);
  });
  setValue('test', 'testData');
}

setValue = function(key, data)
{
  var conn = redis.createClient('redis://fag:cfdd043d458397e295641a103ca70342@50.30.35.9:3008/');
  conn.on('connect', function()
  {
    console.log('Connected');
    conn.set(key, data, function (error, reply)
    {
    if (error)
    {
      console.log(error);
    }
    else {
      console.log(reply);
    }
    }
  );
  }
)
}

getValue = function(key, done)
{
  var conn = redis.createClient('redis://fag:cfdd043d458397e295641a103ca70342@50.30.35.9:3008/');
  conn.on('connect', function()
  {
    console.log('Connected');
    conn.get(key, function (error, reply)
    {
    if (error)
    {
      console.log(error);
    }
    else {
      console.log(reply);
      done(reply);
    }
    }
  );
  }
)
}
