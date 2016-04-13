package group12.whosplayin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by twohyjr on 3/5/16.
 */
class AchievementAdaptor extends ArrayAdapter<Achievement>{

    AchievementAdaptor(Context context, Achievement[] achievements){
        super(context, R.layout.achievment_row,achievements);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater achievementInflator = LayoutInflater.from(getContext());
        View achievementView = achievementInflator.inflate(R.layout.achievment_row, parent, false);
        Achievement singleAchievment = getItem(position);
        TextView achievementTitleText = (TextView) achievementView.findViewById(R.id.achievementTitle);
        TextView achievementDescriptionText = (TextView) achievementView.findViewById(R.id.achievementDescription);
        ImageView achievementImage = (ImageView) achievementView.findViewById(R.id.achievmentImage);

        achievementTitleText.setText(singleAchievment.getTitle());
        achievementDescriptionText.setText(singleAchievment.getDescription());
        //achievementImage.setImageResource(R.drawable.smiley); //needs to change for each achievement

        return achievementView;
    }
}