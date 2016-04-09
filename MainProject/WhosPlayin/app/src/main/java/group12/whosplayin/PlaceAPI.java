package group12.whosplayin;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Jack on 3/4/2016.
 */
public class PlaceAPI
{
    private static final String TAG = PlaceAPI.class.getSimpleName();
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json?";
    private static final String API_KEY = "AIzaSyCqY1w_ERYA3A2CTJl473SxVoF1dHHr-bU"; // Google API Key

    public ArrayList<String> autocomplete(String input) throws IOException {
        System.out.println("AutoComplete Call");

        ArrayList<String> resultList = null;
        HttpURLConnection connection = null;

        StringBuilder jsonResults = new StringBuilder();

        try
        {
            // Build the base API Call String
            StringBuilder builder = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            builder.append("input=" + URLEncoder.encode(input, "utf8"));
            builder.append("&types=establishment");
            builder.append("&key=" + API_KEY);

            System.out.println(builder.toString());

            //Connect to the API
            URL url = new URL(builder.toString());
            connection = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(connection.getInputStream());
            int read;
            char[] buffer = new char[1024];
            while((read = in.read(buffer)) != -1)
            {
                jsonResults.append(buffer, 0, read);
            }
        }

        // Catch and log the errors.
        catch(MalformedURLException e)
        {
            Log.e(TAG, "Error processing Places API URL", e);
        }

        catch(IOException e)
        {
            Log.e(TAG, "Error connecting to Places API", e);
            return resultList;
        }

        // Don't forget to disconenct when you're done.
        finally
        {
            if(connection != null)
            {
                connection.disconnect();
            }
        }

        try
        {
            // Log.d(TAG, jsonResults.toString());

            // Create a JSON Object hierarchy from the results.
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predictionJsonArray = jsonObj.getJSONArray("predictions");

            resultList = new ArrayList<String>(predictionJsonArray.length());

            // Extract the place descriptions from the results.
            for(int i = 0; i < predictionJsonArray.length(); i++)
            {
                resultList.add(predictionJsonArray.getJSONObject(i).getString("description"));
            }
        }

        catch(JSONException e)
        {
            Log.e(TAG, "Cannot process JSON results", e);
        }

        System.out.println(resultList.toString());

        return resultList;
    }
}
