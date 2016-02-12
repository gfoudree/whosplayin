var mysql = require('mysql');
var redis = require('redis');

redisConn = null;
mysqlConn = null;

exports.sqlQuery = function(query, done)
{
  if (mysqlConn != null)
  {
    mysqlConn.getConnection(function(error, connection)
    {
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
      connection.release();
    });
  }
  else
  {
    console.log('Unable to connect to mysql');
    done('Error');
  }
}

exports.mysqlConnect = function()
{
  mysqlConn = mysql.createPool(
    {
      host:'mysql.cs.iastate.edu',
      user:'dbu309grp12',
      database:'db309grp12',
      password:'LPwMxakSPKr',
    }
  );
}

exports.redisConnect = function()
{
  redisConn = redis.createClient('redis://fag:cfdd043d458397e295641a103ca70342@50.30.35.9:3008/');
  redisConn.on('connect', function(err)
  {
    console.log('Error starting redis: ' + err);
  });
}

exports.setValueRedis = function(key, data)
{
  if (redisConn)
  {
      redisConn.set(key, data, function (error, reply)
      {
        console.log('REDIS: ' + key + ' -> ' + data);
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
  else {
    done('Error');
  }
}

exports.getValueRedis = function(key, done)
{
  if (redisConn)
  {
    redisConn.get(key, function (error, reply)
    {
      if (error)
      {
        console.log(error);
      }
      else {
        done(reply);
      }
    }
    );
  }
  else {
    done('Error');
  }
}

exports.listAddRedis = function(key, data, done)
{
  if (redisConn)
  {
    redisConn.sadd(key, data, function (error, reply)
    {
      console.log('REDIS: ' + key + ' -> ' + data);
      if (error)
      {
        console.log(error);
        done(error);
      }
      else {
        console.log(reply);
        done(reply);
      }
    });
  }
  else {
    done('Error');
  }
}

exports.listGetRedis = function(key, done)
{
  if (redisConn)
  {
    redisConn.smembers(key, function (error, reply)
    {
      if (error)
      {
        console.log(error);
        done(error);
      }
      else {
        done(reply);
      }
    }
    );
  }
  else {
    done('Error');
  }
}
