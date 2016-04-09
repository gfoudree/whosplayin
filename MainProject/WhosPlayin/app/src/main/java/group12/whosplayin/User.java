package group12.whosplayin;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by twohyjr on 2/16/16.
 */


public class User {

    //[{"username":"tom","id":1,"name":"Tom Collin","age":20,"gender":"male","location":"Chicago","rating":null,"verified":null,"dateCreated":"2016-02-12T17:07:11.000Z",
    // "lastLogin":null,"picture":null,"gamesPlayed":null,"gamesCreated":null}]

    private String sessionId = "";
    private String email = "";
    private String password = "";
    private String phoneNumber = "";
    public int id = 0;
    public String username = "";
    public String name = "";
    public int age = 0;
    public String gender = "";
    public String location = "";
    public int rating = 0;
    public String verified = "";
    public String dateCreated = "";
    public String profilePicture = "";
    public int gamesPlayed = 0;
    private int gamesCreated = 0;

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
        this.location = obj.getString("location");
        this.rating = obj.getInt("rating");
        this.verified = obj.getString("verified");
        this.dateCreated = obj.getString("dateCreated");
        this.gamesPlayed = obj.getInt("gamesPlayed");
        this.gamesCreated = obj.getInt("gamesCreated");
    }

    public boolean registerUser(String username, String email, String name, String gender, String password, String location, String phoneNumber, String age) throws Exception {
        this.name = name;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.age = Integer.parseInt(age);

        //This replaces all the spaces in the strings with %20 because JSON does not
        //react well to spaces
        name = name.replaceAll("\\s","%20");
        username = username.replaceAll("\\s","%20");
        location = location.replaceAll("\\s","%20");
        password = password.replaceAll("\\s","%20");
        gender = gender.replaceAll("\\s","%20");
        phoneNumber = phoneNumber.replaceAll("\\s","%20");



        //Do for all strings with spaces in them

        HashMap<String,String> regHashMap = new HashMap<>();
        regHashMap.put("username", username);
        regHashMap.put("email",email);
        regHashMap.put("name", name);
        regHashMap.put("gender", gender);
        regHashMap.put("phoneNumber",phoneNumber);
        regHashMap.put("password", password);
        regHashMap.put("location", location);
        regHashMap.put("age",age);

        String url = WebAPI.queryBuilder(regHashMap, null,null);
        String json = "";


        try {
            json = WebAPI.getJson("user/create",url);
        }catch (Exception e){
            Log.d("ERROR", "Error talking to the webapi" + e.getMessage());
        }

        Log.d("Info", json);

        if (json.compareTo("Invalid") != 0)
        {

        }
//        if (json.compareTo("Invalid") != 0)
//        {
//            try
//            {
//                JSONObject obj = new JSONObject(json);
//                String sessId = obj.getString("sessionId");
//                if (sessId != null && !sessId.isEmpty()) {
//                    this.sessionId = sessId;
//                    this.username = username;
//                    getUserId();
//                    return true;
//                }
//                else
//                    return false;
//            }
//            catch (Exception e)
//            {
//                return false;
//            }
//        }


        return false;
    }

    @Override
    public String toString()
    {
        return String.format("%d, %s, %s, %d, %s, %s, %d, %s, %s, %s, %d, %d", id, username, name, age, gender, location, rating, verified, dateCreated, profilePicture, gamesPlayed, gamesCreated);
    }

}
