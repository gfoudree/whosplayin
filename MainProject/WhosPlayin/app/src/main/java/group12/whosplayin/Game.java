package group12.whosplayin;

import android.util.Log;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Jack on 3/4/2016.
 */
public class Game
{

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
    public void createGame(String userName, String sessionID, String eventTitle, String maxPlayers, String startTime, String endTime, String gameType, int captainID) throws Exception
    {
        // We need to get rid of spaces, it messes thing up...
        eventTitle = eventTitle.replaceAll("\\s", "%20");
        startTime = startTime.replaceAll("\\s", "%20");
        endTime = endTime.replaceAll("\\s", "%20");
        gameType = gameType.replaceAll("\\s", "%20");

        // Store the values and key in a hashmap
        HashMap<String, String> create = new HashMap<>();
        create.put("title", eventTitle);
        create.put("maxPlayers", maxPlayers);
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
}
