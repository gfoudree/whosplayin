package group12.whosplayin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * Created by kjdwyer on 2/29/16.
 */
public class Home_Fragment extends Fragment
{
    private Button mCreateGame;
    private Bundle bundle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        bundle = this.getArguments();

        View currentView = inflater.inflate(R.layout.home_layout, container, false);

        mCreateGame = (Button) currentView.findViewById(R.id.createGame_button);
        mCreateGame.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Class fragmentClass = CreateGame_Fragment.class;
                Fragment fragment = null;

                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();
                }

                catch (java.lang.InstantiationException e)
                {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                fragment.setArguments(bundle);

                // DO THE NAVIGATION
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });


        return currentView;
    }
}
