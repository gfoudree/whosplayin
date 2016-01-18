exports.userHandler = function(request, response)
{
  response.send('User ' + request.param('id'));
}
