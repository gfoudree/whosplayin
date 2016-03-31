package group12.whosplayin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Receive the incoming information.
        Bundle incoming = this.getArguments();
        sessionUserName = incoming.getString("USERNAME");
        sessionID = incoming.getString("SESSION_ID");
        sessionUserID = incoming.getInt("USER_ID");
        Log.d("HOME INCOMING BUNDLE", sessionUserName + ", " + sessionUserID + ", " + sessionID);

        View currentView = inflater.inflate(R.layout.home_layout, container, false);


        GetAllGames allGames = new GetAllGames();
        try {
            allGames.getAllGames(sessionUserName, sessionID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Game> gameArray = new ArrayList<Game>();

        // TODO FILL THE GAME ARRAY LIST
        Game game = new Game();
        game.getGameInfo();
        gameArray.add(game);

        Game[] finalGameArray = new Game[gameArray.size()];
        finalGameArray= gameArray.toArray(finalGameArray);



        Log.d("GAME INFO", game.getEventTitle());
        Log.d("ARRAY SIZE", Integer.toString(finalGameArray.length));


        // List View Stuff
        gamesList = (ListView) currentView.findViewById(R.id.listView);
        ListAdapter listAdapter = new MyAdapter(getActivity().getApplicationContext(), finalGameArray);
        gamesList.setAdapter(listAdapter);

        gamesList.setClickable(true);
        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Game currentGame = (Game) gamesList.getItemAtPosition(position);
                int gameId = currentGame.getGameId();

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

                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });

        Log.d("ADAPTER", "Set Adapter");


        mCreateGame = (Button) currentView.findViewById(R.id.createGame_button);
        mCreateGame.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Class fragmentClass = CreateGame_Fragment.class;
                Fragment fragment = null;

                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();
                }

                catch (java.lang.InstantiationException e)
                {
                    e.printStackTrace();
                }

                catch (IllegalAccessException e)
                {
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

    public class MyAdapter extends ArrayAdapter<Game>
    {
        public MyAdapter(Context context, Game[] values)
        {
            super(context, R.layout.gamelist_layout, values);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Log.d("MY ADAPTER", "In MY ADAPTER");
            LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
            View view = inflater.inflate(R.layout.gamelist_layout, parent, false);

            Game game = getItem(position);
            TextView locationText = (TextView) view.findViewById(R.id.location);
            TextView timeText = (TextView) view.findViewById(R.id.gameTime);
            TextView playersText = (TextView) view.findViewById(R.id.numPlayers);
            TextView titleText = (TextView) view.findViewById(R.id.eventTitle);

            titleText.setText(game.getEventTitle());
            Log.d("Title Text", titleText.toString());
            timeText.setText(game.getStartTime() + " - " + game.getEndTime());
            playersText.setText(game.getNumCurrentPlayers() + "/" + game.getMaxPlayers());
            locationText.setText(game.getLocation());

            return view;
        }
    }
}
