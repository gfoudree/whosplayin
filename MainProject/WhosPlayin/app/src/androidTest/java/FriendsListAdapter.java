import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import group12.whosplayin.R;

/**
 * Created by twohyjr on 3/5/16.
 */
public class FriendsListAdapter extends ArrayAdapter {

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
