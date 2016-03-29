package group12.whosplayin;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gfoudree on 2/23/16.
 */
public class WebAPI {
    static final String baseUrl = "http://proj-309-12.cs.iastate.edu:5000/";

    public static String getJson(String path, String query) throws Exception
    {
        URL url = new URL(baseUrl + path);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        DataOutputStream dw = new DataOutputStream(con.getOutputStream());
        dw.writeBytes(query);
        dw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String ret = "", line ="";

        while((line = br.readLine()) != null)
            ret += line;

        br.close();
        dw.close();
        return ret;
    }

    /*
    Username = username
    sessionId = previously authenticated sessionID which is necessary for API calls
    queries is a hashmap of key=value things to be passed as paramters
     */
    public static String queryBuilder(final HashMap<String, String> queries, String username, String sessionId)
    {
        JSONObject json = new JSONObject();

        try {
            for (Map.Entry<String, String> e : queries.entrySet()) {
                json.put(e.getKey(), e.getValue());
            }

            if (username != null && sessionId != null) {
                json.put("username", username);
                json.put("sessionId", sessionId);
            }

        }
        catch (JSONException je)
        {
            Log.d("JSON ERROR", je.toString());
        }
        return json.toString();
    }
}
