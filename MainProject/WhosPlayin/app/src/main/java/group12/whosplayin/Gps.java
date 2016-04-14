package group12.whosplayin;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by gfoudree on 4/12/16.
 */

public class Gps implements LocationListener {
    private LocationManager locationManager;
    private final String provider;

    Gps(Context context)
    {
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        provider = locationManager.getBestProvider(criteria, true);

        try {
            locationManager.requestLocationUpdates(provider, 5000, 0, this); //5 seconds, > 10m distance
        }
        catch (SecurityException se)
        {
            Log.d("ERROR", "Security Exception Error " + se.getMessage());
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        try {
            location = locationManager.getLastKnownLocation(provider);
            GpsPosition pos = GpsPosition.getInstance();

            pos.setLatitude((double) Math.round(location.getLatitude() * 1000000) / 1000000);
            pos.setLongetude((double) Math.round(location.getLongitude() * 1000000) / 1000000);
            Log.d("GPS Position", "Changed");
        }
        catch (SecurityException se)
        {
            Log.d("ERROR", "Security Exception Error " + se.getMessage());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
