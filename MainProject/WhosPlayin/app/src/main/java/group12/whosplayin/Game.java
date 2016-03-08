package group12.whosplayin;

import android.util.Log;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Jack on 3/4/2016.
 */
public class Game
{
    private int gameId;
    private String eventTitle;
    private String location;
    private int maxPlayers;
    private int numCurrentPlayers;
    private String startTime;
    private String endTime;
    private String gameType;
    private int captainId;

    /**
     * Function to create a game. This funciton takes in the parameters passed, builds a URl,
     * and posts the information to datebase.
     *
     * TODO Add Location Support
     * @param userName
     *  userName of the user who created the game. "The Captain"
     * @param sessionID
     *  sessionID of the logged in user.
     * @param eventTitle
     *  title of the event
     * @param maxPlayers
     *  max players allowed in the game.
     * @param startTime
     *  start time of the game.
     * @param endTime
     *  end time of the game.
     * @param gameType
     *  game type to be played.
     * @param captainID
     *  User ID of the person who created the game.
     * @throws Exception
     */
    public void createGame(String userName, String sessionID, String eventTitle, int maxPlayers, String startTime, String endTime, String gameType, int captainID) throws Exception
    {
        // We need to get rid of spaces, it messes thing up...
        eventTitle = eventTitle.replaceAll("\\s", "%20");
        startTime = startTime.replaceAll("\\s", "%20");
        endTime = endTime.replaceAll("\\s", "%20");
        gameType = gameType.replaceAll("\\s", "%20");

        //Update variables
        this.eventTitle = eventTitle;
        this.maxPlayers = maxPlayers;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gameType = gameType;
        this.captainId = captainId;
        this.location = "DUMMY DATA";

        // Store the values and key in a hashmap
        HashMap<String, String> create = new HashMap<>();
        create.put("title", eventTitle);
        create.put("maxPlayers", Integer.toString(maxPlayers));
        create.put("startTime", startTime);
        create.put("endTime", endTime);
        create.put("gameType", gameType);
        create.put("captainId", Integer.toString(captainID));

        // Build the URl.
        String url = WebAPI.queryBuilder(create, userName, sessionID);
        Log.d("URL", url);
        String json = WebAPI.getJson("games/newGame", url);
        Log.d("Create Game Info", json); // Should return success.
    }

    public void getGameInfo()
    {
        // TODO: MAKE API CALL AND SET THE VARIABLES
        //DUMMY DATA FOR NOW
        this.gameId = 1;
        this.eventTitle = "Jack's Test Data";
        this.maxPlayers = 10;
        this.startTime = "2016-03-05 18:45:00";
        this.endTime = "2016-03-05 22:45:00";
        this.gameType = "Basketball";
        this.captainId = 1;
        this.location = "Ames";
    }

    public String getEventTitle()
    {
        return this.eventTitle;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public String getStartTime()
    {
        return this.startTime;
    }

    public String getEndTime()
    {
        return this.endTime;
    }

    public String getGameType()
    {
        return this.gameType;
    }

    public int getCaptainId()
    {
        return this.captainId;
    }

    public String getLocation()
    {
        return this.location;
    }

    public int getNumCurrentPlayers()
    {
        return this.numCurrentPlayers;
    }

    public int getGameId()
    {
        return this.gameId;
    }

}
