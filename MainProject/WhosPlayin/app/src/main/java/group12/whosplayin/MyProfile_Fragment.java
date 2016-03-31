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
    private Button goToButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.myprofile_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        myActivity = this.getActivity();
        goToButton = (Button)myActivity.findViewById(R.id.ButtonClick);

        goToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFriend();
            }
        });

    }

    private void goToFriend(){
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
//        Bundle outgoing = new Bundle();
//        outgoing.putString("USERNAME", sessionUserName);
//        outgoing.putInt("USER_ID", sessionUserID);
//        outgoing.putString("SESSION_ID", sessionId);
//        fragment.setArguments(outgoing);
//
        //Navigating to the fragment
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.flContent,fragment).commit();
    }



}
