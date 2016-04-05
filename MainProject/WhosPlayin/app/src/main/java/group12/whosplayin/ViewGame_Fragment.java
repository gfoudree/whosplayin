package group12.whosplayin;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ViewGame_Fragment extends Fragment
{
    private int gameID;
    private String username;
    private String sessionID;
    private int userID;
    private String title;
    private String startTime;
    private String endTime;
    private String location;
    private ArrayList<User> userArrayList;



    private Game currentGame;

    private TextView mTitle;
    private TextView mLocation;
    private TextView mTime;
    private TextView mGameType;
    private Button mGameControl;
    private TextView mChatText;
    private EditText mMessageText;
    private Button mSendMessage;
    private ListView listView;
    private View currentView;
    public ViewGame_Fragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle incoming = this.getArguments();
        gameID = incoming.getInt("GAME_ID");
        username = incoming.getString("USERNAME");
        sessionID = incoming.getString("SESSION_ID");
        userID = incoming.getInt("USER_ID");
        title = incoming.getString("TITLE");
        startTime = incoming.getString("START_TIME");
        endTime = incoming.getString("END_TIME");
        location = incoming.getString("LOCATION");
        Log.d("INCOMING VIEW GAME", gameID + ", " + username + ", " + sessionID + ", " + userID);


        currentView = inflater.inflate(R.layout.viewgame_layout, container, false);

        mTitle = (TextView) currentView.findViewById(R.id.title_text);
        mLocation = (TextView) currentView.findViewById(R.id.location_text);
        mTime = (TextView) currentView.findViewById(R.id.time_text);
        mGameType = (TextView) currentView.findViewById(R.id.gameType_text);
        mChatText = (TextView) currentView.findViewById(R.id.chat_text);
        mMessageText = (EditText) currentView.findViewById(R.id.message_text);
        mGameControl = (Button) currentView.findViewById(R.id.gameControl_button);
        mSendMessage = (Button) currentView.findViewById(R.id.gameControl_button);
        mTitle.setText(title);
        mLocation.setText(location);
        mTime.setText(startTime + " - " + endTime);
        mLocation.setText(location);


        currentGame = new Game();

        userArrayList = new ArrayList<User>();
        GetUsersInGameTask usersTask = new GetUsersInGameTask(username, sessionID, gameID);
        usersTask.execute((Void) null);


        // On click listeners
        // TODO, WAITING FOR BACKEND STUFF TO GET DONE.
        mGameControl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        // TODO IMPLEMENT THIS WHITH GRANT
        mSendMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddPlayerToGameTask addPlayerToGameTask = new AddPlayerToGameTask(username, sessionID, gameID, userID);
                addPlayerToGameTask.execute((Void) null);
            }
        });

        return currentView;
    }


    public class GetUsersInGameTask extends AsyncTask<Void, Void, Boolean>
    {
        private String username;
        private String sessionID;
        private int gameID;

        GetUsersInGameTask(String username, String sessionID, int gameID)
        {
            this.username = username;
            this.sessionID = sessionID;
            this.gameID = gameID;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try {
                System.out.println(gameID + ", " + username + ", " + sessionID);
//                currentGame.getUsersInGame(gameID, username, sessionID);

                User user = new User();

                user.id = 19;
                user.username = "jack";
                user.name = "Jack Meyer";
                user.age = 20;
                user.gender = "Male";
                user.gamesPlayed = 0;

                userArrayList.add(user);



            } catch (Exception e) {
                e.printStackTrace();
            }

            if(currentGame != null) {
                return true;
            }

            else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            User[] finalUserArray = new User[userArrayList.size()];
            finalUserArray = userArrayList.toArray(finalUserArray);
            makeListView(finalUserArray);
        }
    }

    /**
     * Adapter for the User list view. This is how we define the values that are going to be in the
     * list view.
     */
    public class MyAdapter extends ArrayAdapter<User>
    {
        public MyAdapter(Context context, User[] values)
        {
            super(context, R.layout.userlist_layout, values);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
            View view = inflater.inflate(R.layout.userlist_layout, parent, false);

            User user = getItem(position);
            TextView mFullName = (TextView) view.findViewById(R.id.userFullName);


            mFullName.setText(user.name);

            return view;
        }
    }


    private void makeListView(User[] users)
    {
        // List View Stuff
        listView = (ListView) currentView.findViewById(R.id.gameListView);
        ListAdapter listAdapter = new MyAdapter(getActivity().getApplicationContext(), users);
        listView.setAdapter(listAdapter);

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User currentUser = (User) listView.getItemAtPosition(position);
                int userID = currentUser.getUserId();
                String userName = currentUser.getUsername();
                String name = currentUser.name;

//                Class fragmentClass = ViewGame_Fragment.class;
//                Fragment fragment = null;
//
//                try {
//                    fragment = (Fragment) fragmentClass.newInstance();
//                } catch (java.lang.InstantiationException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }
    /**
     * Task for adding players to the game... This event is launched on join button click.
     */
    public class AddPlayerToGameTask extends AsyncTask<Void, Void, Boolean>
    {
        private String username;
        private String sessionID;
        private int gameID;
        private int userID;

        AddPlayerToGameTask(String username, String sessionID, int gameID, int userID)
        {
            this.username = username;
            this.sessionID = sessionID;
            this.gameID = gameID;
            this.userID = userID;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try {
                System.out.println(gameID + ", " + username + ", " + sessionID);
                currentGame.addPlayerToGame(gameID, userID, username, sessionID);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(currentGame != null) {
                return true;
            }

            else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            return;
        }
    }


}