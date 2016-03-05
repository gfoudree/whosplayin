package group12.whosplayin;


import android.util.Log;

import org.json.JSONObject;

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
    public String locationX = "";
    public String locationY = "";
    public String locationZ = "";
    public String zipcode = "";
    public int rating = 0;
    public int upVotes = 0;
    public int downVotes = 0;
    public String verified = "";
    public String dateCreated = "";
    public String profilePicture = "";
    public int gamesPlayed = 0;
    public int gamesCreated = 0;
    public String bio = "";

    public boolean authenticate(String username, String password) throws Exception
    {
        if (username == null || username.isEmpty() || password == null || password.isEmpty() )
            throw new Exception("Invalid input");

        HashMap<String, String> queries = new HashMap<String, String>();
        queries.put("username", username);
        queries.put("password", password);

        String url = WebAPI.queryBuilder(queries, null, null);
        String json = WebAPI.getJson("user/authenticate", url);
        Log.d("Info", json);
        if (json.compareTo("Invalid") != 0) //Is it valid?
        {
            try
            {
                JSONObject obj = new JSONObject(json);
                this.sessionId = obj.getString("sessionId");
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }
        else
            return false;
    }

    public void getUserInfo() throws Exception
    {
        if (sessionId.isEmpty() || username.isEmpty())
            throw new Exception("No username or session ID!");

        HashMap<String, String> queries = new HashMap<String, String>();
        queries.put("id", "1");

        String url = WebAPI.queryBuilder(queries, username, sessionId); //Replace sessionID with the id after being authenticated
        String json = WebAPI.getJson("user/info", url);

        JSONObject obj = new JSONObject(json);
        this.id = obj.getInt("id");
        this.age = obj.getInt("age");
        this.gender = obj.getString("gender");
        this.locationX = obj.getString("location");
        this.rating = obj.getInt("rating");
        this.verified = obj.getString("verified");
        this.dateCreated = obj.getString("dateCreated");
        this.gamesPlayed = obj.getInt("gamesPlayed");
        this.gamesCreated = obj.getInt("gamesCreated");

        //TODO add upvote/downvote
    }

    @Override
    public String toString()
    {
        return String.format("%d, %s, %s, %d, %s, %s, %d, %s, %s, %s, %d, %d", id, username, name, age, gender, locationX, rating, verified, dateCreated, profilePicture, gamesPlayed, gamesCreated);
    }

}
