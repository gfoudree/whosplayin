package group12.whosplayin;

import android.util.Log;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Jack on 3/4/2016.
 */
public class Game
{
    private int ID;
    private String title;
    private int gameTypeID;
    private int numPlayers;
    private int maxPlayers;
    private String startTime;
    private String endTime;
    private int captainID;
    private int zipCode;
    private String state;
    private String city;
    private String locationName;
    private double latitude;
    private double longitude;
    private double altitude;
    /**
     *
     * @param gameID game's id
     * @param gameTitle title of the game
     * @param gameTypeID id of the game type
     * @param numPlayers number of players currently in the game
     * @param maxPlayers max players allowed in the game
     * @param gameStartTime start time of the game
     * @param gameEndTime end time of the game
     * @param captainID id of the captain of the game
     * @param zipCode zip code for the location of the game
     * @param state state for the location of the game
     * @param city city for the location of the game
     * @param latitude latitude for the location of the game
     * @param longitude longitude for the location of the game
     * @param altitude altiude for the location of the game
     */
    public Game(int gameID, String gameTitle, int gameTypeID, int numPlayers, int maxPlayers,
                String gameStartTime, String gameEndTime, int captainID, int zipCode, String state,
                String city, double latitude, double longitude, double altitude)
    {
        this.ID = gameID;
        this.title = gameTitle;
        this.gameTypeID = gameTypeID;
        this.numPlayers = numPlayers;
        this.maxPlayers = maxPlayers;
        this.startTime = gameStartTime;
        this.endTime = gameEndTime;
        this.captainID = captainID;
        this.zipCode = zipCode;
        this.state = state;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * This is the constructor. This constructor would be used in situations where no information
     * is known yet, for example viewing a game
     */
    public Game()
    {

    }

    public int getID()
    {
        return ID;
    }

    public String getTitle()
    {
        return title;
    }

    public int getGameTypeID()
    {
        return gameTypeID;
    }

    public int getNumPlayers()
    {
        return numPlayers;
    }

    public int getMaxPlayers()
    {
        return maxPlayers;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public int getCaptainID()
    {
        return captainID;
    }

    public int getZipCode()
    {
        return zipCode;
    }

    public String getState()
    {
        return state;
    }

    public String getCity()
    {
        return city;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getAltitude()
    {
        return altitude;
    }


    public void createGame(String userName, String sessionID, int gameID, String gameTitle,
                           int gameTypeID, int numPlayers, int maxPlayers, String gameStartTime,
                           String gameEndTime, int captainID, int zipCode, String state, String city,
                           float latitude, float longitude, float altitude) throws Exception
    {
        //Update variables
        this.title = gameTitle;
        this.maxPlayers = maxPlayers;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gameTypeID = gameTypeID;
        this.captainID = captainID;

        // Store the values and key in a hashmap
        HashMap<String, String> create = new HashMap<>();
        create.put("title", title);
        create.put("maxPlayers", Integer.toString(maxPlayers));
        create.put("startTime", startTime);
        create.put("endTime", endTime);
        create.put("gameType", "5");
        create.put("captainId", Integer.toString(captainID));

        // Build the URl.
        String url = WebAPI.queryBuilder(create, userName, sessionID);
        Log.d("URL", url);
        String json = WebAPI.getJson("games/newGame", url);
        Log.d("Create Game Info", json); // Should return success.
    }

    public void getGameInfo(int gameID, String username, String sessionID)
    {
        // Currently we will just be using dummy data, the api for this portion of the application is
        // currently not completed.

        // CALL QUERY BUILDER RIGHT HERE.

        // fake data
        this.title = "FAKE TITLE";
        this.gameTypeID = 1;
        this.ID = gameID;
        this.maxPlayers = 10;
        this.startTime = "FAKE START TIME";
        this.endTime = "FAKE END TIME";
        this.captainID = 1;
        this.zipCode = 00000;
        this.altitude = 100.000;
        this.latitude = 14.111;
        this.longitude = 14.1111;
        this.locationName = "FAKE LOCATION NAME";
        this.state = "FAKE STATE";
        this.city = "FAKE CITY";
    }

    public void getUsersInGame(int gameID, String username, String sessionID) throws Exception {
        HashMap<String, String> query = new HashMap<>();
        query.put("gameId", Integer.toString(gameID));
        Log.d("INFO", username + ", " + sessionID);
        String url = WebAPI.queryBuilder(query, username, sessionID);
        Log.d("URL", url);
        String json = WebAPI.getJson("games/getPlayers", url);
        Log.d("JSON", json);
    }
}
