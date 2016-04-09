package group12.whosplayin;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

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
        drawerToggle = setupDrawerToggle();

        //Find the drawer view
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        //Then setup drawer view and enable it to navigate between all the fragments
        setupDrawerContent(nvDrawer);

        mDrawer.setDrawerListener(drawerToggle);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.drawer_open,R.string.drawer_close);
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
        android.app.Fragment fragment = null;

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
            case R.id.nav_home_fragment:
                fragmentClass = Home_Fragment.class;
                break;
            case R.id.nav_home2_fragment:
                fragmentClass = Home_Fragment.class;
        }

        //Error Checking to see if user entered the wrong input
        try {
            fragment = (android.app.Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

        //Insert the selected Fragment by replacing the previous Fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();


        //Highlights the item that has been selected and closes the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();

    }



    @Override
    //This method opens or closes the drawer when the action bar home/up action happens.
    //This method also opens the options menu by pressing upper right, and then navigates to
    //desired page
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.misc_menu_logout:
                Intent logoutIntent = new Intent(MainActivity.this, LogoutActivity.class);
                startActivity(logoutIntent);
                return true;
            case R.id.misc_menu_contact:
                Intent contactIntent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(contactIntent);
                return true;
            case R.id.misc_menu_settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.misc_menu_report:
                Intent reportIntent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(reportIntent);
                return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    //Creates an options menu after user logs into app. Because the app uses Fragments
    //for navigation, this method only needs to implemented once.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
