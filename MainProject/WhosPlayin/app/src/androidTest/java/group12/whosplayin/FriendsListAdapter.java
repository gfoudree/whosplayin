package group12.whosplayin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by twohyjr on 3/7/16.
 */
public class FriendsListAdapter extends ArrayAdapter<String> {
    private Context context;
    private int id;
    private List<String> items;


    public FriendsListAdapter(Context context, int textViewResourceId, List<String> colors){
        super(context,textViewResourceId,colors);
        this.context = context;
        this.id = textViewResourceId;
        this.items = colors;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent){
        View view = v;
        if(view == null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(id,null);
        }

        TextView text = (TextView)view.findViewById(R.id.textView);
        text.setTextColor(Color.RED);
        text.setText(items.get(position));
        return view;
    }
}
