#!/usr/bin/perl -W
use strict;
use warnings;
use HTTP::Request::Common;
use LWP::UserAgent;

my $apiKey = "AIzaSyCqHqlv0sg5sKCdHqEnm-hYUox5GpfeJhY"; #AIzaSyCqHqlv0sg5sKCdHqEnm-hYUox5GpfeJhY #AIzaSyB92m2X0UQ3hx9pWchtQiTgSZafI6lk4S4 (GOOGLE TEST)
my $json = '{"data":{"message":"testmsg", "title":"alrt!"},"to":"/topics/global"}';
my $ua = LWP::UserAgent->new(ssl_opts => {verify_hostname => 1});
my $uri = "https://gcm-http.googleapis.com/gcm/send";
my $req = HTTP::Request->new('POST', $uri);

$ua->protocols_allowed(['http', 'https']);
$req->header('Content-Type' => 'application/json');
$req->header('Authorization' => "key=$apiKey");
$req->content($json);

my $res = $ua->request($req);

if ($res->is_success())
{
	print $res;
}
else
{
	print "Fail $!" . $res->status_line;
}
