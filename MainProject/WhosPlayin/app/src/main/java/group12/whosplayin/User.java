package group12.whosplayin;

import org.json.JSONArray;
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
    private int verified = 0;
    private String dateCreated = "";
    private String profilePicture = "";
    private int gamesPlayed = 0;
    private int gamesCreated = 0;
    private String bio = "";
    private int upVotes = 0;
    private int downVotes = 0;
    private String email = "";
    private int timesMvp = 0;
    private double locationLatitude = 0;
    private double locationLongetude = 0;
    private double locationAltitude = 0;
    private int zipcode = 0;
    private String phone = "";
    private String city = "";
    private String state = "";


    public User(int id, String username, String name, int age, String gender, String location, int rating, int verified, String dateCreated, String profilePicture, int gamesPlayed, int gamesCreated)
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

    public void logout() throws Exception
    {
        String resp = WebAPI.getJson("user/logout", WebAPI.queryBuilder(new HashMap<String, String>(), getUsername(), getSessionId()));
        this.sessionId = "";
        this.username = "";
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
            int usrId = obj.getInt("Friend");
            User u = User.getUserInfo(User.getInstance().getUsername(), User.getInstance().getSessionId(), usrId);
            users.add(u);
        }
        return users;
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

    public static User getUserInfo(String username, String sessionId, int id) throws Exception
    {
        if (sessionId.isEmpty() || username.isEmpty())
            throw new Exception("No username or session ID!");

        HashMap<String, String> queries = new HashMap<String, String>();
        queries.put("id", Integer.toString(id));

        String url = WebAPI.queryBuilder(queries, username, sessionId); //Replace sessionID with the id after being authenticated
        String json = WebAPI.getJson("user/info", url);

        if (json.compareTo("Invalid") != 0) {
            JSONArray root = new JSONArray(json);
            JSONArray data = root.getJSONArray(0);
            JSONObject obj = data.getJSONObject(0);
            User u = new User();
            u.setAge(obj.getInt("USR_age"));
            u.setGender(obj.getString("USR_gender"));
            u.setEmail(obj.getString("USR_email"));
            u.setPhone(obj.getString("USR_phone"));
            u.setTimesMvp(obj.getInt("USR_timesMVP"));
            u.setLocationLatitude(obj.getDouble("ULOC_latitude"));
            u.setLocationLongetude(obj.getDouble("ULOC_longitude"));
            u.setLocationAltitude(obj.getDouble("ULOC_altitude"));
            u.setDownVotes(obj.getInt("USR_downVotes"));
            u.setUpVotes(obj.getInt("USR_upVotes"));
            u.setZipcode(obj.getInt("ULOC_zipcode"));
            u.setVerified(obj.getInt("USR_verified"));
            u.setGamesPlayed(obj.getInt("USR_gamesPlayed"));
            u.setGamesCreated(obj.getInt("USR_gamesCreated"));
            u.setUsername(obj.getString("USR_username"));
            u.setName(obj.getString("USR_name"));
            u.setAge(obj.getInt("USR_age"));
            u.setState(obj.getString("ULOC_state"));
            u.setCity(obj.getString("ULOC_city"));
            return u;
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

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
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

    public int getUpVotes(){return upVotes;}

    public int getDownVotes(){return downVotes;}

    public void setUsername(String username)
    {
        this.username = username;
    }

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

    public User[] getUserFriends(){
        User tempUser1 = new User();
        tempUser1.name = "Rick";
        tempUser1.username = "twohyjr";
        tempUser1.id = 18;
        User tempUser2 = new User();
        tempUser2.name = "Mike";
        tempUser2.username = "mikejr";
        tempUser2.id = 20;

        User[] friends = {tempUser1,tempUser2};
        return friends;
    }

    public Achievement[] getUserAchievements(){
        Achievement ach1 = new Achievement();
        ach1.setTitle("AWESOME");
        ach1.setDescription("TOTALLY AWESOME");

        Achievement ach2 = new Achievement();
        ach2.setTitle("Even More BA");
        ach2.setDescription("TOTALLY BA");

        Achievement[] achievements = {ach1,ach2};
        return achievements;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTimesMvp() {
        return timesMvp;
    }

    public void setTimesMvp(int timesMvp) {
        this.timesMvp = timesMvp;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongetude() {
        return locationLongetude;
    }

    public void setLocationLongetude(double locationLongetude) {
        this.locationLongetude = locationLongetude;
    }

    public double getLocationAltitude() {
        return locationAltitude;
    }

    public void setLocationAltitude(double locationAltitude) {
        this.locationAltitude = locationAltitude;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public int getZipcode()
    {
        return zipcode;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

