var express = require('express');
var helmet = require('helmet');
var sqlinjection = require('sql-injection');
var bodyParser = require('body-parser');

var users = require('./users');
var db = require('./db');
var games = require('./games');

var app = express();
//var https = require('https');
//var fs = require('fs');

app.use(sqlinjection);
app.use(bodyParser.urlencoded({extended: true}));
app.use(helmet()); //Sets security headers for HTTP
app.disable('x-powered-by');
app.set('port', 5000);

db.redisConnect();
db.mysqlConnect();

//var pKey = fs.readFileSync('key.pem');
//var cert = fs.readFileSync('cert.pem');
//var cred = {key: pKey, cert: cert};

//var httpsServer = https.createServer(cred, app);
app.get('/', function(request, response) //Main index
{
  response.send('<a href=http://localhost:5000/user/authenticate?user=tom&password=password>Login</a>');
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
