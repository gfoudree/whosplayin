package group12.whosplayin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

        //Sha256 hash of password
        DataOutputStream ds = new DataOutputStream(con.getOutputStream());
        ds.writeBytes(query);
        ds.flush();
        ds.close();

        BufferedReader bReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String ret = "", line ="";

        while((line = bReader.readLine()) != null)
            ret += line;

        bReader.close();
        return ret;
    }

    /*
    Username = username
    sessionId = previously authenticated sessionID which is necessary for API calls
    queries is a hashmap of key=value things to be passed as paramters
     */
    public static String queryBuilder(final HashMap<String, String> queries, String username, String sessionId)
    {
        String params = "";
        for (Map.Entry<String, String> e : queries.entrySet())
        {
            if (!params.isEmpty())
                params += "&";
            params += String.format("%s=%s", e.getKey(), e.getValue());
        }

        if (username != null && sessionId != null)
            params += String.format("&username=%s&sessionId=%s", username, sessionId);

        return params;
    }
}
