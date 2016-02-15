package com.rickstestapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsicLUT;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class TestUserPage extends AppCompatActivity {


    private TextView thumbsDownText;
    private TextView thumbsUpText;
    private TextView ageText;
    private TextView zipText;
    private TextView genderText;
    private TextView lastActiveText;
    private TextView username;
    private TextView friendZone;
    private Button addFriend;
    private ScrollView achievmentsView;


    public Boolean secure = false;
    public Boolean friend = false;
    public String name = "Madman123";
    public String age = "23";
    public String zipcode = "12564";
    public String gender = "Female";
    public String lastActive = "01/20/2015";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_user_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        thumbsDownText = (TextView) findViewById(R.id.thumbsDownText);
        thumbsUpText = (TextView) findViewById(R.id.thumbsUpText);
        username = (TextView) findViewById(R.id.userNameText);
        ageText = (TextView) findViewById(R.id.ageText);
        zipText = (TextView) findViewById(R.id.zipText);
        genderText = (TextView) findViewById(R.id.genderText);
        lastActiveText = (TextView) findViewById(R.id.lastActiveDate);
        friendZone = (TextView) findViewById(R.id.friendZoneText);
        addFriend = (Button) findViewById(R.id.addFriendButton);
        achievmentsView = (ScrollView) findViewById(R.id.achievmentsView);


        username.setText(name);
        ageText.setText(age);
        zipText.setText(zipcode);
        genderText.setText(gender);
        lastActiveText.setText(lastActive);
        getFriendZone();
        getAchievments();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void getAchievments(){
        int achievementWidth = 100;

        ImageView iv = new ImageView(achievmentsView.getContext());
        iv.setBackgroundColor(Color.RED);
        iv.setVisibility(View.VISIBLE);
        iv.setMinimumWidth(achievementWidth);
        iv.setMinimumHeight(achievementWidth);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(achievementWidth,achievementWidth);
        params.setMargins(50,50,50,50);
        achievmentsView.addView(iv,params);

    }

    private void getFriendZone() {
        if (friend) {
            friendZone.setText("FRIEND");
            friendZone.setTextColor(Color.GREEN);
        } else {
            friendZone.setText("NOT A FRIEND");
            friendZone.setTextColor(Color.RED);
        }
    }

    public void thumbsUpPressed(View v) {
        int up = Integer.parseInt(thumbsUpText.getText().toString());
        up += 1;
        thumbsUpText.setText("" + up);
    }

    public void thumbsDownPressed(View v) {
        int down = Integer.parseInt(thumbsDownText.getText().toString());
        down += 1;
        thumbsDownText.setText("" + down);
    }

    public void addFriendButton(View v) {
        if(friend = false) {
            friend = true;
            getFriendZone();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TestUserPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.rickstestapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TestUserPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.rickstestapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
