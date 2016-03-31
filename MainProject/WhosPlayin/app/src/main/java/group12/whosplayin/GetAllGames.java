package group12.whosplayin;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by Jack on 3/29/2016.
 */
public class GetAllGames
{
    public static String getAllGames(String username, String sessionID) throws Exception {

        String url = WebAPI.queryBuilder(new HashMap<String, String>(), username, sessionID);
        String json = WebAPI.getJson("games/getCurrentGames", url);

        return json;
    }
}
