var pg = require('pg');
var mysql = require('mysql');
var redis = require('redis');

exports.sqlQuery = function(query, done)
{
  var connection = mysql.createConnection(
    {
      host:'sql5.freemysqlhosting.net',
      user:'sql5103427',
      database:'sql5103427',
      password:'qTJw2dqrvs'
    }
  );

  connection.connect();
  connection.query(query, function(err, rows, field)
  {
    if (err)
    {
      console.log(err);
      done('Error retrieving SQL data');
    }
    else {
      done(rows);
    }
  }
  );
  connection.end();
}


exports.setValueRedis = function(key, data)
{
  var conn = redis.createClient('redis://fag:cfdd043d458397e295641a103ca70342@50.30.35.9:3008/');
  conn.on('connect', function()
  {
    console.log(key + ' -> ' + data);
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

exports.getValueRedis = function(key, done)
{
  var conn = redis.createClient('redis://fag:cfdd043d458397e295641a103ca70342@50.30.35.9:3008/');
  conn.on('connect', function()
  {
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
