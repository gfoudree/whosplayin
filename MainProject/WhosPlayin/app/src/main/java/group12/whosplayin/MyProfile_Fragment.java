package group12.whosplayin;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kjdwyer on 2/29/16.
 */
public class MyProfile_Fragment extends Fragment{

    private User currentUser;
    private int sessionUserID;
    private String sessionUserName;
    private String sessionId;

    private Activity myActivity;

    private TextView usernameLabel;
    private TextView editProfileButton;
    private TextView usersNameView;
    private TextView usersAgeView;
    private TextView usersGenderView;
    private TextView usersBioView;
    private ListView friendsListView;


    private User myUser;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Retrieve user information
        Bundle incoming = this.getArguments();
        sessionUserName = incoming.getString("USERNAME");
        sessionId = incoming.getString("SESSION_ID");
        sessionUserID = incoming.getInt("USER_ID");

        //LOG THE CREDETIALS HERE
        Log.d("userLog", "Username:  " + sessionUserName + "\n"
                     + "User ID:   " + sessionUserID + "\n"
                    + "SessionID: " + sessionId + "\n"

        );

        myUser = new User();
        myUser.getUserData(sessionUserID);



        return inflater.inflate(R.layout.myprofile_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        myActivity = this.getActivity();

        usernameLabel = (TextView)myActivity.findViewById(R.id.usernameLabel);
        usersNameView = (TextView)myActivity.findViewById(R.id.userNameLabel);
        usersAgeView = (TextView)myActivity.findViewById(R.id.usersAgeLabel);
        usersGenderView = (TextView)myActivity.findViewById(R.id.usersGenderLabel);
        usersBioView = (TextView)myActivity.findViewById(R.id.userBioLabel);
        friendsListView = (ListView)myActivity.findViewById(R.id.friendsListView);

        editProfileButton = (TextView)myActivity.findViewById(R.id.editProfileButton);
        friendsListView = (ListView)this.getActivity().findViewById(R.id.friendsListView);

        User[] friends = myUser.getUserFriends();
        ListAdapter friendsAdapter = new FriendsAdaptor(getActivity().getApplicationContext(),friends);
        friendsListView.setAdapter(friendsAdapter);

        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) friendsListView.getItemAtPosition(position);
                goToFriend(user);
            }
        });

        setLabelData();
    }

    private void setLabelData(){
        usernameLabel.setText(myUser.username);
        usersNameView.setText(myUser.name);
        usersAgeView.setText(String.valueOf(myUser.age));
        usersBioView.setText(myUser.bio);
        usersGenderView.setText(myUser.gender);
    }


    private void goToFriend(User user){
        Class fragmentClass = FriendsProfile_Fragment.class;
        Fragment fragment = null;

        try{
            fragment = (Fragment) fragmentClass.newInstance();
        }catch(java.lang.InstantiationException ex){
            ex.printStackTrace();
        }catch (IllegalAccessException ex){
            ex.printStackTrace();
        }

        //Outgoing bundle
        Bundle outgoing = new Bundle();
        outgoing.putInt("USER_ID", user.id);
        fragment.setArguments(outgoing);
//
        //Navigating to the fragment
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.flContent,fragment).commit();
    }



}
