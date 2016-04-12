package group12.whosplayin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
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
    private Button mMapView;
    private ListView gamesList;
    ArrayList<Game> gameArray;
    View currentView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        currentView = inflater.inflate(R.layout.home_layout, container, false);

        gameArray = new ArrayList<Game>();
        GetAllGamesTask task = new GetAllGamesTask();
        task.execute((Void) null);

        mMapView = (Button) currentView.findViewById(R.id.viewmap_button);
        mMapView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }

        });

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

                // DO THE NAVIGATION
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });


        return currentView;
    }

    /**
     * Async task for getting all of the current games. It gets all the current games and then on
     * post execute sets the list view adapter.
     */
    public class GetAllGamesTask extends AsyncTask<Void, Void, Boolean>
    {
        GetAllGamesTask()
        {

        }

        @Override
        protected Boolean doInBackground(Void... params){
            Game game = new Game();
            try
            {
                gameArray = game.getCurrentGames(User.getInstance());
                return true;
            }

            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            // If we were successful getting the current arrays, we'll make the lsit view.
            if(success)
            {
                    Game[] finalGameArray = new Game[gameArray.size()];
                    finalGameArray = gameArray.toArray(finalGameArray);
                    makeListView(finalGameArray);
            }

            else {
                Log.e("ERROR", "ERROR");
            }
        }
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
            locationText.setText(game.getGameLocation());

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
                int gameId = currentGame.getId();
                String title = currentGame.getTitle();
                String startTime = currentGame.getStartTime();
                String endTime = currentGame.getEndTime();
                String location = currentGame.getGameLocation();

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


                // Outgoing Bundle. Need to send Game_ID, title, start time, end time, and location.
                Bundle outgoing = new Bundle();
                outgoing.putInt("GAME_ID", gameId);
                outgoing.putString("TITLE", title);
                outgoing.putString("START_TIME", startTime);
                outgoing.putString("END_TIME", endTime);
                outgoing.putString("LOCATION", location);
                fragment.setArguments(outgoing);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });
    }
}
