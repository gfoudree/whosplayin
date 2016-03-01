package group12.whosplayin;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //The toolbar replaces the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Now I find the drawer view that is defined by the drawer_layout.xml file
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Find the drawer view
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        //Then setup drawer view and enable it to navigate between all the fragments
        setupDrawerContent(nvDrawer);
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new  NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public  boolean  onNavigationItemSelected(MenuItem  menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem){
        Fragment fragment = null;

        Class fragmentClass = null;

        switch(menuItem.getItemId()) {
            //Whichever tab on the navbar is clicked, i.e. My Profile, Events, Achievements, etc.,
            //That class/id is stored in fragmentClass so it can be manipulated
            case R.id.nav_profile_fragment:
                fragmentClass = MyProfile_Fragment.class;
                break;
            case R.id.nav_friends_fragment:
                fragmentClass = Friends_Fragment.class;
                break;
            case R.id.nav_events_fragment:
                fragmentClass = Events_Fragment.class;
                break;
            case R.id.nav_messages_fragment:
                fragmentClass = Messages_Fragment.class;
                break;
            case R.id.nav_achievements_fragment:
                fragmentClass = Achievements_Fragment.class;
                break;
            case R.id.nav_discover_fragment:
                fragmentClass = Discover_Fragment.class;
                break;
        }

        //Error Checking to see if user entered the wrong input
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

//        FragmentManager fragmentManager = getSupportFragmentManager();

    }

    @Override
    //This method opens or closes the drawer when the action bar home/up action happens
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
