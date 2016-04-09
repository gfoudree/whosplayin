package group12.whosplayin;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    public String getSessionId()
    {
	    return sessionId;
    }

    public String getUsername()
    {
        return username;
    }

    public int getUserId()
    {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("user", this.username);
        String url = WebAPI.queryBuilder(queries, this.username, this.sessionId);
        String json = "";
        try {
            json = WebAPI.getJson("user/getId", url);
        }
        catch (Exception e)
        {
            Log.d("ERROR", "Error talking to the webapi" + e.getMessage());
            return -1;
        }
        try {
                JSONArray ja = new JSONArray(json);
                JSONObject obj = ja.getJSONObject(0);
                int userId = obj.getInt("USR_id");
                this.id = userId;
                return userId;
        }
        catch (JSONException e)
        {
                Log.d("ERROR", "JSON ERROR");
                return -1;
        }
    }

    public boolean authenticate(String username, String password) throws Exception
    {
        if (username == null || username.isEmpty() || password == null || password.isEmpty() )
            throw new Exception("Invalid input");

        HashMap<String, String> queries = new HashMap<String, String>();
        queries.put("username", username);
        queries.put("password", password);

        String url = WebAPI.queryBuilder(queries, null, null);
        String json = "";

        try {
            json = WebAPI.getJson("user/authenticate", url);
        }
        catch (Exception e)
        {
            Log.d("ERROR", "Error talking to the webapi" + e.getMessage());
        }

        Log.d("Info", json);
        if (json.compareTo("Invalid") != 0) //Is it valid?
        {
            try
            {
                JSONObject obj = new JSONObject(json);
                String sessId = obj.getString("sessionId");
                if (sessId != null && !sessId.isEmpty()) {
                    this.sessionId = sessId;
                    this.username = username;
                    getUserId();
                    return true;
                }
                else
                    return false;
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

        Log.d("TESTING", this.gender);
    }

    public User getUserData(int userID){
        username = "twohyjr";
        name = "Rick Twohy";
        gender = "Male";
        bio = "I'm cool as fuzz.  My middle name is Jasper.  Play with me!";
        age = 17;
        id = 17;
        return this;
    }


    //Works with fake data.  Needs web Api.
    public User[] getUserFriends(){

        User friend1 = new User();
        friend1.name = "Friend1";
        friend1.id = 1;

        User friend2 = new User();
        friend2.name = "Friend2";
        friend2.id = 2;

        User friend3 = new User();
        friend3.name = "Friend3";
        friend3.id = 3;
        User[] friendsList = {friend1,friend2,friend3};

        return friendsList;
    }

    public Game[] getUserGames(){
        Game game1 = new Game();
        game1.setEventTitle("Game 1");

        Game game2 = new Game();
        game2.setEventTitle("Game2");

        return new Game[]{game1,game2};
    }

    //Works with fake data.  Needs web Api.
    public Achievement[] getAchievements(){
        Achievement[] achievements;

        Achievement achievement1 = new Achievement();
        achievement1.setTitle("GOOD JOB");
        achievement1.setDescription("You did a good job");

        Achievement achievement2 = new Achievement();
        achievement2.setTitle("BETTER JOB");
        achievement2.setDescription("You did a better than good job");

        Achievement achievement3 = new Achievement();
        achievement3.setTitle("MVP");
        achievement3.setDescription("You are a 5 time mvp");

        achievements = new Achievement[]{achievement1,achievement2,achievement3};

        return achievements;
    }

    public void getVotes(){

    }

    @Override
    public String toString()
    {
        return String.format("%d, %s, %s, %d, %s, %s, %d, %s, %s, %s, %d, %d", id, username, name, age, gender, locationX, rating, verified, dateCreated, profilePicture, gamesPlayed, gamesCreated);
    }

}
