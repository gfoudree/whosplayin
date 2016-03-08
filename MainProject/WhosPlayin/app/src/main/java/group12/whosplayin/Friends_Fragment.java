package group12.whosplayin;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kjdwyer on 2/29/16.
 */
public class Friends_Fragment extends Fragment{


    private Activity myActivity;
    private TextView ageText;
    private TextView usernameText;
    private TextView zipText;
    private TextView bioText;
    private TextView genderText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.myprofile_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        myActivity = this.getActivity();
        genderText = (TextView)myActivity.findViewById(R.id.MYP_genderView);
        ageText = (TextView)myActivity.findViewById(R.id.MYP_ageView);
        usernameText = (TextView)myActivity.findViewById(R.id.MYP_usernameView);
        zipText = (TextView)myActivity.findViewById(R.id.MYP_zipcodeView);
        bioText = (TextView)myActivity.findViewById(R.id.MYP_bioView);
    }

    public void setUserInfo(User pageUser){
        pageUser.username = "2etime";
        pageUser.age = 27;
        pageUser.gender = "male";
        pageUser.zipcode = "95336";
        pageUser.bio = "I LIKE GAMES";

        ageText.setText(pageUser.age);
        usernameText.setText(pageUser.username);
        zipText.setText(pageUser.zipcode);
        bioText.setText(pageUser.bio);
        genderText.setText(pageUser.gender);
    }

}
