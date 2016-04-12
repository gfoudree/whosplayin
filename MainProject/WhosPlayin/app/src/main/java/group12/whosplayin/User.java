package group12.whosplayin;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class User {
    private static User instance;

    private String sessionId = "";
    public int id = 0;
    private String username = "";
    private String name = "";
    private int age = 0;
    private String gender = "";
    private String location = "";
    private int rating = 0;
    private String verified = "";
    private String dateCreated = "";
    private String profilePicture = "";
    private int gamesPlayed = 0;
    private int gamesCreated = 0;
    private String bio = "";
    private String zipcode = "";
    private int upVotes = 0;
    private int downVotes = 0;


    public User(int id, String username, String name, int age, String gender, String location, int rating, String verified, String dateCreated, String profilePicture, int gamesPlayed, int gamesCreated)
    {
        this.id = id;
        this.username = username;
        this.setName(name);
        this.setAge(age);
        this.setGender(gender);
        this.setLocation(location);
        this.setRating(rating);
        this.setVerified(verified);
        this.setDateCreated(dateCreated);
        this.setProfilePicture(profilePicture);
        this.setGamesPlayed(gamesPlayed);
        this.setGamesCreated(gamesCreated);
    }

    public User()
    {

    }

    public String getSessionId()
    {
        return sessionId;
    }

    public String getUsername()
    {
        return username;
    }

    public static synchronized User getInstance()
    {
        if (instance == null)
            instance = new User();
        return instance;
    }

    public static boolean createUser(String username, String password, String name, int age, String gender, String email, String phone) throws Exception
    {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("username", username);
        queries.put("password", password);
        queries.put("name", name);
        queries.put("age", Integer.toString(age));
        queries.put("gender", gender);
        queries.put("email", email);
        queries.put("phone", phone);

        String url = WebAPI.queryBuilder(queries, null, null);
        String json = WebAPI.getJson("user/create", url);

        if (json.compareTo("Success") == 0)
            return true;
        else
            return false;

    }

    public ArrayList<User> getFriends(int userId) throws Exception {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("id", Integer.toString(userId));
        String url = WebAPI.queryBuilder(queries, this.username, this.sessionId);

        String json = WebAPI.getJson("user/getFriendsList", url);

        JSONArray root = new JSONArray(json);
        JSONArray data = root.getJSONArray(0);
        ArrayList<User> users = new ArrayList<User>();

        for (int i = 0; i < data.length(); i++)
        {
            JSONObject obj = data.getJSONObject(i);

        }

        return null;

    }

    public void addFriend(int user, int friendIdToAdd) throws Exception
    {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("userId", Integer.toString(user));
        queries.put("friendId", Integer.toString(friendIdToAdd));
        String url = WebAPI.queryBuilder(queries, this.username, this.sessionId);
        String json = WebAPI.getJson("user/addFriend", url);
    }

    public int getUserId() throws Exception
    {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("user", this.username);
        String url = WebAPI.queryBuilder(queries, this.username, this.sessionId);
        String json = WebAPI.getJson("user/getId", url);

        if (!json.isEmpty()) {
            JSONArray ja = new JSONArray(json);
            JSONObject obj = ja.getJSONObject(0);
            int userId = obj.getInt("USR_id");
            return userId;
        }
        else
        {
            throw new Exception("Error getting userID from WEBAPI");
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
            return false;
        }

        if (json.compareTo("Invalid") != 0 && !json.isEmpty()) //Is it valid?
        {
            try
            {
                JSONObject obj = new JSONObject(json);
                String sessId = obj.getString("sessionId");
                if (sessId != null && !sessId.isEmpty()) {
                    this.sessionId = sessId;
                    this.username = username;
                    this.id = getUserId();
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

        if (json.compareTo("Success") == 0) {
            JSONObject obj = new JSONObject(json);
            this.id = obj.getInt("id");
            this.setAge(obj.getInt("age"));
            this.setGender(obj.getString("gender"));
            this.setLocation(obj.getString("location"));
            this.setRating(obj.getInt("rating"));
            this.setVerified(obj.getString("verified"));
            this.setDateCreated(obj.getString("dateCreated"));
            this.setGamesPlayed(obj.getInt("gamesPlayed"));
            this.setGamesCreated(obj.getInt("gamesCreated"));
        }
        else
            throw new Exception("Error getting user info from WebAPI");
    }

    @Override
    public String toString(){
        return String.format("%d, %s, %s, %d, %s, %s, %d, %s, %s, %s, %d, %d", id, username, getName(), getAge(), getGender(), getLocation(), getRating(), getVerified(), getDateCreated(), getProfilePicture(), getGamesPlayed(), getGamesCreated());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesCreated() {
        return gamesCreated;
    }

    public void setGamesCreated(int gamesCreated) {
        this.gamesCreated = gamesCreated;
    }




    public String getBio(){
        return bio;
    }

    public String getZipcode(){return zipcode;}

    public int getUpVotes(){return upVotes;}

    public int getDownVotes(){return downVotes;}

    public void getUserInfo(int userId){
        User tempUser = new User();
        tempUser.name = "Rick";
        tempUser.username = "twohyjr";
        tempUser.id = userId;
    }

    public Game[] getUserGames(){
        Game game1 = new Game();
        game1.setTitle("Game 1");
        game1.setId(0);

        Game game2 = new Game();
        game2.setTitle("Game 1");
        game2.setId(0);

        Game[] games = {game1,game2};
        return games;
    }
   }
