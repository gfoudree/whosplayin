var express = require('express');
var helmet = require('helmet');
var sqlinjection = require('sql-injection');
var bodyParser = require('body-parser');

var users = require('./users');
var db = require('./db');
var games = require('./games');

var app = express();
app.use(sqlinjection);
app.use(bodyParser.urlencoded({extended: true}));
app.use(helmet()); //Sets security headers for HTTP
app.disable('x-powered-by');
app.set('port', (process.env.PORT || 5000));

db.redisConnect();
db.mysqlConnect();

app.get('/', function(request, response) //Main index
{
  response.send('Hello world');
});

/* ---------------------- USERS --------------------------- */
app.get('/user', users.userHandler); //Users
app.get('/user/authenticate', users.userAuthenticator);
app.get('/user/friendsList', users.friendsList);

/* ---------------------- GAMES --------------------------- */
app.get('/games', games.getGames);
app.get('/games/new', games.newGame);

/* ---------------------- MESSAGES --------------------------- */

app.listen(app.get('port'), function() //Main loop
{
  console.log('Running web!');
});
