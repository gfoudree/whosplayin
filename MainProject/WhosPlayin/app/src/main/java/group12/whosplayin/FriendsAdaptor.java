package group12.whosplayin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by twohyjr on 4/2/16.
 */
class FriendsAdaptor extends ArrayAdapter<User> {

    public FriendsAdaptor(Context context, User[] friends){
        super(context, R.layout.friend_row,friends);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater friendsListInflator = LayoutInflater.from(getContext());
        View friendsView = friendsListInflator.inflate(R.layout.friend_row, parent, false);

        User singleUser = getItem(position);


        TextView friendName = (TextView) friendsView.findViewById(R.id.friendRow_friendsNameLabel);
        ImageView friendProfilePic = (ImageView) friendsView.findViewById(R.id.friendRow_profilePicImage);

        friendName.setText(singleUser.name);
        friendProfilePic.setImageResource(R.drawable.smiley); //needs to change for each achievement

        return friendsView;
    }


}