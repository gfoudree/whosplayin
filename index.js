var http = require("http");
var url = require('url');

http.createServer(
function(request, response)
{
  var url_parts = url.parse(request.url, true);
  console.log(url_parts.query);
  if (url_parts.query['t'] == '3')
  {
    console.log('yes!')
  }
response.writeHead(200, {'Content-Type' : 'text/plain'});
response.end('helffflo');
}
).listen(8081);

console.log('server running');
