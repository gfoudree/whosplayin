package group12.whosplayin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kjdwyer on 2/29/16.
 */
public class Home_Fragment extends Fragment
{
    private Button mCreateGame;
    private String sessionUserName;
    private String sessionID;
    private int sessionUserID;
    private ListView gamesList;
    ArrayList<Game> gameArray;
    View currentView;

    private String allGamesString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Receive the incoming information.
        Bundle incoming = this.getArguments();
        sessionUserName = incoming.getString("USERNAME");
        sessionID = incoming.getString("SESSION_ID");
        sessionUserID = incoming.getInt("USER_ID");
        Log.d("HOME INCOMING BUNDLE", sessionUserName + ", " + sessionUserID + ", " + sessionID);

        currentView = inflater.inflate(R.layout.home_layout, container, false);

        gameArray = new ArrayList<Game>();
        GetAllGamesTask task = new GetAllGamesTask(sessionUserName, sessionID);
        task.execute((Void) null);



        Log.d("ADAPTER", "Set Adapter");


        mCreateGame = (Button) currentView.findViewById(R.id.createGame_button);
        mCreateGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Class fragmentClass = CreateGame_Fragment.class;
                Fragment fragment = null;

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                // Create the outgoing bundle
                Bundle outgoing = new Bundle();
                outgoing.putString("USERNAME", sessionUserName);
                outgoing.putInt("USER_ID", sessionUserID);
                outgoing.putString("SESSION_ID", sessionID);
                fragment.setArguments(outgoing);

                // DO THE NAVIGATION
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });


        return currentView;
    }

    public class GetAllGamesTask extends AsyncTask<Void, Void, Boolean>
    {
        private final String username;
        private final String sessionID;

        GetAllGamesTask(String username, String sessionID)
        {
            this.username = username;
            this.sessionID = sessionID;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            GetAllGames allGames = new GetAllGames();
            try {
                // Get all of the games.
                allGamesString = allGames.getAllGames(this.username, this.sessionID);
                int index = allGamesString.indexOf("],");
                allGamesString = allGamesString.substring(1);
                allGamesString = allGamesString.substring(0, index);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success) {
                try {
                    // Turn it into a JSON object.
                    JSONArray jsonArray = new JSONArray(allGamesString);
                    Game[] games = getGameArrayList(jsonArray);
                    makeListView(games);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else {
                Log.e("ERROR", "ERROR");
            }
        }
    }

    private Game[] getGameArrayList(JSONArray array) throws JSONException {
        // Loop through and add our results to an array!
        for(int i = 0; i < array.length(); i++)
        {
            JSONObject obj = array.getJSONObject(i);
            int id = obj.getInt("GAM_id");
            String title = obj.getString("GAM_title");
            int gameTypeID = obj.getInt("GAM_gameTypeID");
            int currentPlayers = obj.getInt("GAM_numPlayers");
            int maxPlayers = obj.getInt("GAM_maxPlayers");
            String startTime = obj.getString("GAM_startTime");
            String endTime = obj.getString("GAM_endTime");
            int captainID = obj.getInt("GAM_captainID");

            Game game = new Game(id, title, gameTypeID, currentPlayers, maxPlayers, startTime,
                    endTime, captainID, 00000, "FAKE STATE", "FAKE CITY",
                    0.000, 0.000, 000);
            gameArray.add(game);

        }


        Game[] finalGameArray = new Game[gameArray.size()];
        finalGameArray = gameArray.toArray(finalGameArray);

        return finalGameArray;
    }

    /**
     * Adapter which helps in displaying the data on the list view. On the list we are going to show
     * the location, the time, current players, and title.
     */
    public class MyAdapter extends ArrayAdapter<Game>
    {
        public MyAdapter(Context context, Game[] values)
        {
            super(context, R.layout.gamelist_layout, values);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
            View view = inflater.inflate(R.layout.gamelist_layout, parent, false);

            Game game = getItem(position);
            TextView locationText = (TextView) view.findViewById(R.id.location);
            TextView timeText = (TextView) view.findViewById(R.id.gameTime);
            TextView playersText = (TextView) view.findViewById(R.id.numPlayers);
            TextView titleText = (TextView) view.findViewById(R.id.eventTitle);

            titleText.setText(game.getTitle());
            Log.d("Title Text", titleText.toString());
            timeText.setText(game.getStartTime() + " - " + game.getEndTime());
            playersText.setText(game.getNumPlayers() + "/" + game.getMaxPlayers());
            locationText.setText(game.getCity() + game.getState());

            return view;
        }
    }

    /**
     * Helper function that helps create the list view.
     * @param games array of all of the current games.
     */
    private void makeListView(Game[] games)
    {
        // List View Stuff
        gamesList = (ListView) currentView.findViewById(R.id.listView);
        ListAdapter listAdapter = new MyAdapter(getActivity().getApplicationContext(), games);
        gamesList.setAdapter(listAdapter);

        gamesList.setClickable(true);
        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Game currentGame = (Game) gamesList.getItemAtPosition(position);
                int gameId = currentGame.getID();
                String title = currentGame.getTitle();
                String startTime = currentGame.getStartTime();
                String endTime = currentGame.getEndTime();
                String location = currentGame.getLocationName();

                Class fragmentClass = ViewGame_Fragment.class;
                Fragment fragment = null;

                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();
                }

                catch(java.lang.InstantiationException e)
                {
                    e.printStackTrace();
                }

                catch(IllegalAccessException e)
                {
                    e.printStackTrace();
                }

                Bundle outgoing = new Bundle();
                outgoing.putInt("GAME_ID", gameId);
                outgoing.putString("USERNAME", sessionUserName);
                outgoing.putString("SESSION_ID", sessionID);
                outgoing.putInt("USER_ID", sessionUserID);
                outgoing.putString("TITLE", title);
                outgoing.putString("START_TIME", startTime);
                outgoing.putString("END_TIME", endTime);
                outgoing.putString("LOCATION", location);
                Log.d("LV CLICK OUTGOING", gameId + ", " + sessionUserName + ", " + sessionID);
                fragment.setArguments(outgoing);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });
    }
}
