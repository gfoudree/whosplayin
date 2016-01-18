var pg = require('pg');
var mysql = require('mysql');

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
    done(rows);
  }
);
  connection.end();
}
