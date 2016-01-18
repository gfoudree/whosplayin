var express = require('express');
var helmet = require('helmet');


var users = require('./users');
var db = require('./db');

var app = express();
app.use(helmet()); //Sets security headers for HTTP
app.disable('x-powered-by');
app.set('port', (process.env.PORT || 5000));


app.get('/', function(request, response) //Main index
{
  response.send('Hello world');
  db.sqlQuery('select * from user', function(rows)
  {
    console.log(rows[0]['username']);
  }
);
});

app.get('/user', users.userHandler); //Users

app.listen(app.get('port'), function() //Main loop
{
  console.log('Running web!');
});
