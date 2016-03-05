package group12.whosplayin;

import android.util.Log;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Jack on 3/4/2016.
 */
public class Game
{
    public void createGame(String userName, String sessionID, String eventTitle, String maxPlayers, String startTime, String endTime, String gameType, int captainID) throws Exception
    {
        HashMap<String, String> create = new HashMap<>();
        create.put("title", eventTitle);
        create.put("maxPlayers", maxPlayers);
        create.put("startTime", startTime);
        create.put("endTime", endTime);
        create.put("gameType", gameType);
        create.put("captainId", Integer.toString(captainID));

        String url = WebAPI.queryBuilder(create, userName, sessionID);
        String json = WebAPI.getJson("games/newGame", url);
        Log.d("Create Game Info", json);





    }

}
