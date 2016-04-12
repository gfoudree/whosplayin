package group12.whosplayin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kjdwyer on 2/29/16.
 */
public class MyProfile_Fragment extends Fragment{

        private User currentUser;
        private int sessionUserID;
        private String sessionUserName;
        private String sessionId;

        private Button goToFriendsButton;
        private Button goToGamesButton;
        private Button goToAchievementsButton;

        private TextView usernameLabel;
        private TextView editProfileButton;
        private TextView usersNameView;
        private TextView usersAgeView;
        private TextView usersGenderView;
        private TextView usersBioView;

        private User myUser;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //Retrieve user information
            Bundle incoming = this.getArguments();
            sessionUserName = incoming.getString("USERNAME");
            sessionId = incoming.getString("SESSION_ID");
            sessionUserID = incoming.getInt("USER_ID");

            Log.d("Passed","Username: " + sessionUserName);
            Log.d("Passed","UserID: " + sessionUserID);
            Log.d("Passed","SessionID: " + sessionId);




            myUser = new User();
            myUser.getUserInfo(19);
            Log.d("Info", "UserName: " + myUser.getSessionId());



            return inflater.inflate(R.layout.myprofile_layout, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            usernameLabel = (TextView) getActivity().findViewById(R.id.usernameLabel);
            usersNameView = (TextView) getActivity().findViewById(R.id.userNameLabel);
            usersAgeView = (TextView) getActivity().findViewById(R.id.usersAgeLabel);
            usersGenderView = (TextView) getActivity().findViewById(R.id.usersGenderLabel);
            usersBioView = (TextView) getActivity().findViewById(R.id.userBioLabel);

            editProfileButton = (TextView) getActivity().findViewById(R.id.editProfileButton);
            goToGamesButton = (Button) getActivity().findViewById(R.id.myGamesListButton);
            goToFriendsButton = (Button) getActivity().findViewById(R.id.goToFriendsListButton);
            goToAchievementsButton = (Button) getActivity().findViewById(R.id.myAchievementsButton);

            goToFriendsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToFriends();
                }
            });

            goToGamesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToGames();
                }
            });

            goToAchievementsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAchievments();
                }
            });

            editProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToEditProfile();
                }
            });


            setLabelData();
        }

        private void goToAchievments() {
            Class fragmentClass = Achievements_Fragment.class;
            Fragment fragment = null;

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (java.lang.InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }

            //Outgoing bundle
            Bundle outgoing = new Bundle();
            outgoing.putString("USERNAME", sessionUserName);
            outgoing.putString("SESSION_ID", sessionId);
            outgoing.putInt("USER_ID", sessionUserID);
            fragment.setArguments(outgoing);
//
            //Navigating to the fragment
            FragmentManager manager = this.getFragmentManager();
            manager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        private void goToFriends() {
            Class fragmentClass = Friends_Fragment.class;
            Fragment fragment = null;

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (java.lang.InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }

            //Outgoing bundle
            Bundle outgoing = new Bundle();
            outgoing.putString("USERNAME", sessionUserName);
            outgoing.putString("SESSION_ID", sessionId);
            outgoing.putInt("USER_ID", sessionUserID);
            fragment.setArguments(outgoing);
//
            //Navigating to the fragment
            FragmentManager manager = this.getFragmentManager();
            manager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        private void goToGames() {
            Class fragmentClass = Games_Fragment.class;
            Fragment fragment = null;

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (java.lang.InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }

            //Outgoing bundle
            Bundle outgoing = new Bundle();
            outgoing.putString("USERNAME", "MIKE");
            outgoing.putString("SESSION_ID", "DAVE");
            outgoing.putInt("USER_ID", 2);
            fragment.setArguments(outgoing);
//
            //Navigating to the fragment
            FragmentManager manager = this.getFragmentManager();
            manager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        private void goToEditProfile() {

        }

        private void setLabelData() {
            usernameLabel.setText(myUser.getUsername());
            usersNameView.setText(myUser.getName());
            usersAgeView.setText(String.valueOf(myUser.getAge()));
            usersBioView.setText(myUser.getBio());
            usersGenderView.setText(myUser.getGender());
        }
    }

