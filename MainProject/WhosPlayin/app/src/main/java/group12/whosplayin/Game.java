package group12.whosplayin;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class Game {
    
    private int id;
    private String title;
    private int gameType;
    private int numPlayers;
    private int maxPlayers;
    private String dateCreated;
    private String startTime;
    private String endTime;
    private int captainId;
    private int zipcode;
    private double altitude;
    private double longitude;
    private double latitude;
    private String gameLocation;
    private String state;
    private String city;

    public Game(int id, String title, int gameType, int numPlayers, int maxPlayers, String dateCreated, String startTime, String endTime, int captainId, double latitude, double longitude, String gameLocation)

    {
        this.setId(id);
        this.setTitle(title);
        this.setNumPlayers(numPlayers);
        this.setMaxPlayers(maxPlayers);
        this.setGameType(gameType);
        this.setDateCreated(dateCreated);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setCaptainId(captainId);
        this.setGameLocation(gameLocation);
    }
    
    public Game() {
        
    }
    
    public static boolean getPlayersInGame(User user, int gameId) throws Exception {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("gameId", Integer.toString(gameId));
        
        String url = WebAPI.queryBuilder(queries, user.getUsername(), user.getSessionId());
        Log.d("URL", url);
        String json = WebAPI.getJson("games/getPlayers", url);
        
        Log.d("JSON", json);
        return true;
    }
    
    public static boolean removeUserFromGame(User user, int userId, int gameId) throws Exception
    {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("gameId", Integer.toString(gameId));
        queries.put("userId", Integer.toString(userId));
        
        String url = WebAPI.queryBuilder(queries, user.getUsername(), user.getSessionId());
        String json = WebAPI.getJson("games/removeUserFromGame", url);
        if (json.compareTo("Success") == 0)
            return true;
        else
            return false;
    }
    
    public static boolean addPlayerToGame(User user, int gameId, int userId) throws Exception
    {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("gameId", Integer.toString(gameId));
        queries.put("userId", Integer.toString(userId));
        
        String url = WebAPI.queryBuilder(queries, user.getUsername(), user.getSessionId());
        String json = WebAPI.getJson("games/addPlayerToGame", url);
        if (json.compareTo("Success") == 0)
            return true;
        else
            return false;
    }
    
    public static ArrayList<Game> getCurrentGames(User user) throws Exception
    {
        ArrayList<Game> games = new ArrayList<Game>();
        HashMap<String, String> queries = new HashMap<>();
        String url = WebAPI.queryBuilder(queries, user.getUsername(), user.getSessionId());
        
        String json = WebAPI.getJson("games/getCurrentGames", url);
        
        JSONArray root = new JSONArray(json);
        JSONArray data = root.getJSONArray(0);
        
        for (int i = 0; i < data.length(); i++)
        {
            JSONObject obj = data.getJSONObject(i);
            
            Game gameObj = new Game(
                    obj.getInt("GAM_id"),
                    obj.getString("GAM_title"),
                    obj.getInt("GAM_gameTypeID"),
                    obj.getInt("GAM_numPlayers"),
                    obj.getInt("GAM_maxPlayers"),
                    obj.getString("GAM_dateCreated"),
                    obj.getString("GAM_startTime"),
                    obj.getString("GAM_endTime"),
                    obj.getInt("GAM_captainID"),
                    obj.getDouble("GAM_latitude"),
                    obj.getDouble("GAM_longitude"),
                    obj.getString("GAM_locationName")
                    );
            
            games.add(gameObj);
        }
        return games;
    }
    
    public static boolean createGame(User user, Game game) throws Exception
    {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("gameTitle", game.getTitle());
        queries.put("gameTypeID", Integer.toString(game.getGameType()));
        queries.put("numPlayers", Integer.toString(game.getNumPlayers()));
        queries.put("maxPlayers", Integer.toString(game.getMaxPlayers()));
        queries.put("dateCreated", game.getDateCreated());
        queries.put("startTime", game.getStartTime());
        queries.put("endTime", game.getEndTime());
        queries.put("captainID", Integer.toString(game.getCaptainId()));
        queries.put("zipcode", Integer.toString(game.getZipcode()));
        queries.put("altitude", Double.toString(game.getAltitude()));
        queries.put("longitude", Double.toString(game.getLongitude()));
        queries.put("latitude", Double.toString(game.getLatitude()));
        queries.put("state", game.getState());
        queries.put("city", game.getCity());
        
        String url = WebAPI.queryBuilder(queries, user.getUsername(), user.getSessionId());
        Log.d("URL", url);
        String json = WebAPI.getJson("games/newGame", url);
        if (json.compareTo("Success") == 0)
            return true;
        else
            return false;
        
    }
    
    public String toString()
    {
        return String.format("%d, %s, %d, %d, %d, %s, %s, %s, %d", id, title, gameType, numPlayers, maxPlayers, dateCreated, startTime, endTime, captainId);
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getNumPlayers() {
        return numPlayers;
    }
    
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
    
    public int getGameType() {
        return gameType;
    }
    
    public void setGameType(int gameType) {
        this.gameType = gameType;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getMaxPlayers() {
        return maxPlayers;
    }
    
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    
    public String getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public String getStartTime() {
        return startTime;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public int getCaptainId() {
        return captainId;
    }
    
    public void setCaptainId(int captainId) {
        this.captainId = captainId;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public String getEndTime() {
        return this.endTime;
    }
    
    public int getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
    
    public double getAltitude() {
        return altitude;
    }
    
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public Double getLatitude()
    {
        return this.latitude;
    }
    
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getGameLocation() {
        return gameLocation;
    }

    public void setGameLocation(String gameLocation) {
        this.gameLocation = gameLocation;
    }
}