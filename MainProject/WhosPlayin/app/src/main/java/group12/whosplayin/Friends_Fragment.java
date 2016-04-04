package group12.whosplayin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by kjdwyer on 2/29/16.
 */
public class Friends_Fragment extends Fragment{

    private String sessionUserName;
    private String sessionId;
    private int sessionUserID;
    private User myUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle incoming = this.getArguments();
        sessionUserName = incoming.getString("USERNAME");
        sessionId = incoming.getString("SESSION_ID");
        sessionUserID = incoming.getInt("USER_ID");

        myUser = new User();
        myUser.getUserData(sessionUserID);
        return inflater.inflate(R.layout.friends_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        Button goBackButton = (Button)getActivity().findViewById(R.id.addFriendButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToProfile();
            }
        });

        User[] friends = myUser.getUserFriends();
        ListAdapter friendsAdapter = new FriendsAdaptor(getActivity(),friends);
        final ListView friendsListView = (ListView)this.getActivity().findViewById(R.id.friendsListView);
        friendsListView.setAdapter(friendsAdapter);

        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) friendsListView.getItemAtPosition(position);
                goToFriendsProfile(user.id);
            }
        });
    }

    private void goToFriendsProfile(int userId){
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
        outgoing.putInt("USER_ID", userId);
        fragment.setArguments(outgoing);
//
        //Navigating to the fragment
        FragmentManager manager = this.getFragmentManager();
        manager.beginTransaction().replace(R.id.flContent,fragment).commit();
    }

    public void goBackToProfile(){
        Class fragmentClass = MyProfile_Fragment.class;
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
        outgoing.putString("USERNAME",sessionUserName);
        outgoing.putString("SESSION_ID",sessionId);
        outgoing.putInt("USER_ID", sessionUserID);
        fragment.setArguments(outgoing);
//
        //Navigating to the fragment
        FragmentManager manager = this.getFragmentManager();
        manager.beginTransaction().replace(R.id.flContent,fragment).commit();
    }

    public Boolean addFriend(){

        return false;

    }

    public void sendUserMessage(){

    }

}
