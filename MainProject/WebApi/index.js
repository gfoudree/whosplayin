var express = require('express');
var bodyParser = require('body-parser');
var helmet = require('helmet');

var users = require('./users');
var db = require('./db');
var games = require('./games');

var app = express();
//var https = require('https');
//var fs = require('fs');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use(helmet()); //Sets security headers for HTTP
app.disable('x-powered-by');

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
app.post('/user/info', users.getInfo); //Users
app.post('/user/authenticate', users.authenticate);
app.post('/user/getFriendsList', users.getFriendsList);
app.post('/user/create', users.create);
app.post('/user/status', users.status);
app.post('/user/getId', users.getId);
app.post('/user/logout', users.logout);

/* ---------------------- GAMES --------------------------- */
app.post('/games/info', games.getGames);
app.post('/games/newGame', games.newGame);
app.post('/games/addPlayerToGame', games.addPlayer);
app.post('/games/getPlayers', games.getPlayers);
app.post('/games/getCurrentGames', games.getGames);
app.post('/games/removeUserFromGame', games.removeUserFromGame);

/* ---------------------- MESSAGES --------------------------- */

app.listen(5000);
