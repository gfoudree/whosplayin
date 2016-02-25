var express = require('express');
var app = express();
var port = process.env.PORT || 5000;

var bodyParser = require('body-parser');
app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true })); // support encoded bodies

app.post('/user/authenticate', function(request, response)
{
  console.log(request.body.username);
  response.send("Aa");
})

app.listen(port);
console.log('Server started! At http://localhost:' + port);
