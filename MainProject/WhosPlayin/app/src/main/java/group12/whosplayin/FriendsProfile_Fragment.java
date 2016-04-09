package group12.whosplayin;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kjdwyer on 2/29/16.
 */
public class FriendsProfile_Fragment extends Fragment{

    private User currentUser;
    private int sessionUserID;
    private String sessionUserName;
    private String sessionId;

    private Activity myActivity;
    private TextView ageText;
    private TextView usernameText;
    private TextView zipText;
    private TextView bioText;
    private TextView upVoteText;
    private TextView downVoteText;
    private TextView genderText;
    private TextView gamesPlayedText;

    private Button sendMessageButton;
    private Button addFriendButton;

    private User myUser;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//Retrieve user information
        Bundle incoming = this.getArguments();
        sessionUserID = incoming.getInt("USER_ID");

        //LOG THE CREDETIALS HERE
        Log.d("userLog", "Username:  " + sessionUserName + "\n"
                        + "User ID:   " + sessionUserID + "\n"
                        + "SessionID: " + sessionId + "\n"

        );

        myUser = new User();
        myUser.getUserData(sessionUserID);
        return inflater.inflate(R.layout.friends_profile_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        myActivity = this.getActivity();
        genderText = (TextView)myActivity.findViewById(R.id.genderText);
        ageText = (TextView)myActivity.findViewById(R.id.ageText);
        usernameText = (TextView)myActivity.findViewById(R.id.usernameView);
        zipText = (TextView)myActivity.findViewById(R.id.zipText);
        bioText = (TextView)myActivity.findViewById(R.id.bioText);
        upVoteText = (TextView)myActivity.findViewById(R.id.upVoteText);
        downVoteText = (TextView)myActivity.findViewById(R.id.downVoteText);
        gamesPlayedText = (TextView)myActivity.findViewById(R.id.gamesPlayedText);
        sendMessageButton = (Button)myActivity.findViewById(R.id.messageButton);
        addFriendButton = (Button)myActivity.findViewById(R.id.addFriendButton);

        usernameText.setText(myUser.getName());
        genderText.setText(myUser.getGender());
        ageText.setText(String.valueOf(myUser.getAge()));
        zipText.setText(myUser.getZipcode());
        bioText.setText(myUser.getBio());
        upVoteText.setText(String.valueOf(myUser.getUpVotes()));
        downVoteText.setText(String.valueOf(myUser.getDownVotes()));
        gamesPlayedText.setText(String.valueOf(myUser.getGamesPlayed()));

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserMessage();
            }
        });
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });

//        Achievement[] achievements = getAchievements();
//        ListAdapter friendsAdapter = new AchievmentAdapter(myActivity,achievements);
//        ListView friendsListView = (ListView)this.getActivity().findViewById(R.id.AchievementsListView);
//        friendsListView.setAdapter(friendsAdapter);


    }

    public Boolean addFriend(){
        Snackbar.make(this.getView(), "Friend Request Sent", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//        int userId = 18;
//        int friendId = 21;
//
//        HashMap<String, String> queries = new HashMap<String, String>();
//        queries.put("userID", String.valueOf(userId));
//        queries.put("friendID", String.valueOf(friendId));
//
//        String url = WebAPI.queryBuilder(queries, "rick", "Session Id Here");
//        String json = "";
//        try{
//            json = WebAPI.getJson("user/addFriend", url);
//        }catch(Exception ex){
//
//        }
//
//        Log.d("Info", json);
//        if (json.compareTo("Invalid") != 0) //Is it valid?
//        {
//            try
//            {
//                JSONObject obj = new JSONObject(json);
//                this.sessionId = obj.getString("sessionId");
//                return true;
//            }
//            catch (Exception e)
//            {
//                return false;
//            }
//        }
//        else
            return false;

    }

    public void sendUserMessage(){
        Class fragmentClass = Messages_Fragment.class;
        Fragment fragment = null;

        try{
            fragment = (Fragment) fragmentClass.newInstance();
        }catch(java.lang.InstantiationException ex){
            ex.printStackTrace();
        }catch (IllegalAccessException ex){
            ex.printStackTrace();
        }
//
//        //Outgoing bundle
//        Bundle outgoing = new Bundle();
//        outgoing.putInt("USER_ID", userId);
//        fragment.setArguments(outgoing);
//
        //Navigating to the fragment
        FragmentManager manager = this.getFragmentManager();
        manager.beginTransaction().replace(R.id.flContent,fragment).commit();
    }

}
