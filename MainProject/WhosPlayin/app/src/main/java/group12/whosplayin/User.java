package group12.whosplayin;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;


public class MainActivity extends AppCompatActivity{
    
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private String sessionUserName;
    private String sessionID;
    private int userID;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent = getIntent();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
            }
        });
        
        //The toolbar replaces the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        //Now I find the drawer view that is defined by the drawer_layout.xml file
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        
        //Find the drawer view
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        //Then setup drawer view and enable it to navigate between all the fragments
        setupDrawerContent(nvDrawer);
        
        mDrawer.setDrawerListener(drawerToggle);
    }
    
    private ActionBarDrawerToggle setupDrawerToggle()
    {
        return new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.drawer_open,R.string.drawer_close);
    }
    
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                                                         new  NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public  boolean  onNavigationItemSelected(MenuItem  menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }
    
    public void selectDrawerItem(MenuItem menuItem){
        android.app.Fragment fragment = null;
        
        Class fragmentClass = null;
        
        switch(menuItem.getItemId()) {
                //Whichever tab on the navbar is clicked, i.e. My Profile, Events, Achievements, etc.,
                //That class/id is stored in fragmentClass so it can be manipulated
            case R.id.nav_profile_fragment:
                fragmentClass = MyProfile_Fragment.class;
                break;
            case R.id.nav_friends_fragment:
                fragmentClass = Friends_Fragment.class;
                break;
            case R.id.nav_events_fragment:
                fragmentClass = Events_Fragment.class;
                break;
            case R.id.nav_messages_fragment:
                fragmentClass = Messages_Fragment.class;
                break;
            case R.id.nav_achievements_fragment:
                fragmentClass = Achievements_Fragment.class;
                break;
            case R.id.nav_discover_fragment:
                fragmentClass = Discover_Fragment.class;
                break;
            case R.id.nav_home_fragment:
                fragmentClass = Home_Fragment.class;
                break;
            case R.id.nav_home2_fragment:
                fragmentClass = Home_Fragment.class;
        }
        
        //Error Checking to see if user entered the wrong input
        try
        {
            fragment = (android.app.Fragment) fragmentClass.newInstance();
        }
        
        catch (Exception e){
            e.printStackTrace();
        }
        
        // WE NEED TO PASS THE FRAGMENTS DATA!!!!
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", sessionUserName);
        bundle.putInt("USER_ID", userID);
        bundle.putString("SESSION_ID", sessionID);
        
        fragment.setArguments(bundle);
        
        
        //Insert the selected Fragment by replacing the previous Fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
        
        
        //Highlights the item that has been selected and closes the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
        
    }
    
    
    
    @Override
    //This method opens or closes the drawer when the action bar home/up action happens
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}

package group12.whosplayin;


import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;


public class User {
    private static User instance;
    
    private String sessionId = "";
<<<<<<< HEAD
    private int id = 0;
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
    
    public ArrayList<User> getFriends(int userId) throws Exception
    {
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
    
=======


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

>>>>>>> parent of 3f38c9a... Merge branch 'rick_user_profile_view'
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
<<<<<<< HEAD
                String sessId = obj.getString("sessionId");
                if (sessId != null && !sessId.isEmpty()) {
                    this.sessionId = sessId;
                    this.username = username;
                    this.id = getUserId();
=======
                this.sessionId = obj.getString("sessionId");
                if (sessionId != null && !sessionId.isEmpty())
>>>>>>> parent of 3f38c9a... Merge branch 'rick_user_profile_view'
                    return true;
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
<<<<<<< HEAD
        
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
    public String toString()
    {
        return String.format("%d, %s, %s, %d, %s, %s, %d, %s, %s, %s, %d, %d", id, username, getName(), getAge(), getGender(), getLocation(), getRating(), getVerified(), getDateCreated(), getProfilePicture(), getGamesPlayed(), getGamesCreated());
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    private int getAge() {
        return age;
    }
    
    private void setAge(int age) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
=======

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

    @Override
    public String toString()
    {
        return String.format("%d, %s, %s, %d, %s, %s, %d, %s, %s, %s, %d, %d", id, username, name, age, gender, location, rating, verified, dateCreated, profilePicture, gamesPlayed, gamesCreated);
>>>>>>> parent of 3f38c9a... Merge branch 'rick_user_profile_view'
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
    
}