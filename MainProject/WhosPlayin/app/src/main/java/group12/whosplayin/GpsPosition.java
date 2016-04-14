package group12.whosplayin;

import java.util.ArrayList;

/**
 * Created by gfoudree on 4/13/16.
 */
public class GpsPosition {
    private static GpsPosition gpsPosition;

    private ArrayList<Double> pastLatitudes;
    private ArrayList<Double> pastLongitudes;
    private double currentLatitude;
    private double currentLongitude;

    public static synchronized GpsPosition getInstance()
    {
        if (gpsPosition == null)
            gpsPosition = new GpsPosition();
        return gpsPosition;
    }

    GpsPosition()
    {
        pastLatitudes = new ArrayList<>();
        pastLongitudes = new ArrayList<>();
    }

    public ArrayList<Double> getPastLatitudes() {
        return pastLatitudes;
    }

    public ArrayList<Double> getPastLongitudes() {
        return pastLongitudes;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setLongitude(double longe)
    {
        currentLongitude = longe;
        pastLongitudes.add(longe);
    }

    public void setLatitude(double lat)
    {
        currentLatitude = lat;
        pastLatitudes.add(lat);
    }
}
