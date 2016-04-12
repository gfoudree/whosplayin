package com.example.gfoudree.androidservice;

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

    private double lat;
    private double lng;

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
            Log.d("ERr", "Err");
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        try {
            location = locationManager.getLastKnownLocation(provider);
            lat = ((double) Math.round(location.getLatitude() * 1000000) / 1000000);
            lat = ((double) Math.round(location.getLongitude() * 1000000) / 1000000);
        }
        catch (SecurityException se)
        {
            Log.d("ERr", "Err");
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

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

}
