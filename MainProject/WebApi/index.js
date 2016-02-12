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
app.get('/user/info', users.getInfo); //Users
app.get('/user/authenticate', users.authenticator);
app.get('/user/friendsList', users.getFriendsList);
app.get('/user/create', users.create);

/* ---------------------- GAMES --------------------------- */
app.get('/games/info', games.getGames);
app.get('/games/newGame', games.newGame);
app.get('/games/addplayer', games.addPlayer);
app.get('/games/getPlayers', games.getPlayers);

/* ---------------------- MESSAGES --------------------------- */

app.listen(app.get('port'), function() //Main loop
{
  console.log('Running web!');
});
