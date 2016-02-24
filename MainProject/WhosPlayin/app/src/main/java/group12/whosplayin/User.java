package group12.whosplayin;


import java.util.HashMap;

/**
 * Created by twohyjr on 2/16/16.
 */


public class User {

    //[{"username":"tom","id":1,"name":"Tom Collin","age":20,"gender":"male","location":"Chicago","rating":null,"verified":null,"dateCreated":"2016-02-12T17:07:11.000Z",
    // "lastLogin":null,"picture":null,"gamesPlayed":null,"gamesCreated":null}]

    private String sessionId = "";


    public int id = 0;
    public String username = "";
    public String name = "";
    public int age = 0;
    public String gender = "";
    public String location = "";
    public int rating = 0;
    public String verified = "";
    public String dateCreated = "";
    public String lastLogin = "";
    public String profilePicture = "";
    public int gamesPlayed = 0;
    private int gamesCreated = 0;

    public void authenticate() throws Exception
    {
        HashMap<String, String> queries = new HashMap<String, String>();
        queries.put("username", "tom");
        queries.put("password", "password");

        String url = WebAPI.queryBuilder(queries, null, null);
        String json = WebAPI.getJson("user/authenticate", url);
    }

    public void getUserInfo() throws Exception
    {
        if (sessionId.isEmpty() || username.isEmpty())
            throw new Exception("No username or session ID!");

        HashMap<String, String> queries = new HashMap<String, String>();
        queries.put("id", "1");

        String url = WebAPI.queryBuilder(queries, username, sessionId); //Replace sessionID with the id after being authenticated
        String json = WebAPI.getJson("user/info", url);
    }

}
