package group12.whosplayin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Game> gameArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        GetAllGamesTask getAllGamesTask = new GetAllGamesTask();
        getAllGamesTask.execute((Void) null);
    }

    /**
     * Async task that gets all the games. On post execute, it will display the location on the google
     * maps.
     */
    public class GetAllGamesTask extends AsyncTask<Void, Void, Boolean> {

        private Game currentGame;
        GetAllGamesTask() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Game game = new Game();
            try {
                gameArray = game.getCurrentGames(User.getInstance());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                for (int i = 0; i < gameArray.size(); i++) {
                    currentGame = gameArray.get(i);
                    Double lat = currentGame.getLatitude();
                    Double lng = currentGame.getLongitude();
                    Log.d("Lat Long", Double.toString(lat) + ", " + Double.toString(lng));
                    mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat, lng))
                                    .title(Integer.toString(currentGame.getId()))
                    );

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            Log.d("MARKER ID", marker.getTitle());
                            Game gameToView = null;

                            for(int i = 0; i < gameArray.size(); i++)
                            {
                                if(gameArray.get(i).getId() == Integer.parseInt(marker.getTitle()))
                                {
                                    gameToView = gameArray.get(i);
                                }
                            }


                            Class fragmentClass = ViewGame_Fragment.class;
                            Fragment fragment = null;
                            try {
                                 fragment = (android.app.Fragment) fragmentClass.newInstance();

                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                            Bundle outgoing = new Bundle();
                            outgoing.putInt("GAME_ID", gameToView.getId());
                            outgoing.putString("TITLE", gameToView.getTitle());
                            outgoing.putString("START_TIME", gameToView.getStartTime());
                            outgoing.putString("END_TIME", gameToView.getEndTime());
                            outgoing.putString("LOCATION", gameToView.getGameLocation());


                            fragment.setArguments(outgoing);


                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

                            return true;
                        }
                    });
                }
            } else {
                Log.e("ERROR", "ERROR");
            }
        }
    }
}
