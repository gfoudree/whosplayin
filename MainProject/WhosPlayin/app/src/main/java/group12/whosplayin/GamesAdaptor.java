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
class GamesAdaptor extends ArrayAdapter<Game> {

    public GamesAdaptor(Context context, Game[] games){
        super(context, R.layout.game_row, games);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater gamesListInflator = LayoutInflater.from(getContext());
        View friendsView = gamesListInflator.inflate(R.layout.game_row, parent, false);

        Game singleGame = getItem(position);


        TextView gameTitle = (TextView) friendsView.findViewById(R.id.gameTitle);
        ImageView gameProfilePic = (ImageView) friendsView.findViewById(R.id.gamePic);

        gameTitle.setText(singleGame.getTitle());
        //gameProfilePic.setImageResource(Change Here); //needs to change for each achievement

        return friendsView;
    }


}