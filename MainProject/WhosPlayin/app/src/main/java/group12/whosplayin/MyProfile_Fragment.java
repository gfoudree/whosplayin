package group12.whosplayin;

import android.app.Activity;
import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    private Activity myActivity;
    private TextView ageText;
    private TextView usernameText;
    private TextView zipText;
    private TextView bioText;
    private TextView upVoteText;
    private TextView downVoteText;
    private TextView genderText;
    private TextView gamesPlayedText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.myprofile_layout, container, false);
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


        //setUserInfo(new User());
        Achievement[] achievements = getAchievements();
        ListAdapter friendsAdapter = new AchievmentAdapter(myActivity,achievements);
        ListView friendsListView = (ListView)this.getActivity().findViewById(R.id.AchievementsListView);
        friendsListView.setAdapter(friendsAdapter);

        friendsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String color = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(myActivity,color,Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void setUserInfo(User pageUser){
        pageUser.username = "2etime";
        pageUser.gamesPlayed = 5;
        pageUser.age = 27;
        pageUser.gender = "male";
        pageUser.gamesPlayed = 10;
        pageUser.upVotes = 5;
        pageUser.downVotes = 10;
        pageUser.zipcode = "95336";
        pageUser.bio = "I LIKE GAMES";

        ageText.setText(pageUser.age);
        usernameText.setText(pageUser.username);
        zipText.setText(pageUser.zipcode);
        bioText.setText(pageUser.bio);
        upVoteText.setText(pageUser.upVotes);
        downVoteText.setText(pageUser.downVotes);
        gamesPlayedText.setText(pageUser.gamesPlayed);
        genderText.setText(pageUser.gender);
    }

    public Achievement[] getAchievements(){
        Achievement achievement1 = new Achievement();
        achievement1.setTitle("GOOD JOB");
        achievement1.setDescription("You did a good job");

        Achievement achievement2 = new Achievement();
        achievement2.setTitle("BETTER JOB");
        achievement2.setDescription("You did a better than good job");

        Achievement achievement3 = new Achievement();
        achievement3.setTitle("MVP");
        achievement3.setDescription("You are a 5 time mvp");

        Achievement[] achievements = {achievement1,achievement2,achievement3};

        return achievements;
    }
}
