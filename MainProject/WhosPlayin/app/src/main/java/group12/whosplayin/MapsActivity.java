package group12.whosplayin;

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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });
        GetAllGamesTask getAllGamesTask = new GetAllGamesTask();
        getAllGamesTask.execute((Void) null);
    }

    /**
     * Async task that gets all the games. On post execute, it will display the location on the google
     * maps.
     */
    public class GetAllGamesTask extends AsyncTask<Void, Void, Boolean> {
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
                    Game currentGame = gameArray.get(i);
                    LatLng latLng = new LatLng(currentGame.getLatitude(), currentGame.getLongitude());

                }
            } else {
                Log.e("ERROR", "ERROR");
            }
        }
    }
}
