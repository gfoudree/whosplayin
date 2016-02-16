var mysql = require('mysql');
var redis = require('redis');

redisConn = null;
mysqlConn = null;

var sqlQuery = function(query, done)
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

var mysqlConnect = function()
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

var redisConnect = function()
{
  redisConn = redis.createClient('redis://proj-309-12.cs.iastate.edu:6379/');
  redisConn.on('connect', function(err)
  {
    console.log('Error starting redis: ' + err);
  });
}

var setValueRedis = function(key, data)
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

var getValueRedis = function(key, done)
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

var listAddRedis = function(key, data, done)
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

var listGetRedis = function(key, done)
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

module.exports = {
  listGetRedis: listGetRedis,
  sqlQuery: sqlQuery,
  listAddRedis: listAddRedis,
  getValueRedis: getValueRedis,
  setValueRedis: setValueRedis,
  redisConnect: redisConnect,
  mysqlConnect: mysqlConnect
}
