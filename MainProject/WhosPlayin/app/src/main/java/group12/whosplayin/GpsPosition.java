package group12.whosplayin;

import java.util.ArrayList;

/**
 * Created by gfoudree on 4/13/16.
 */
public class GpsPosition {
    private static GpsPosition gpsPosition;

    private ArrayList<Double> pastLatitudes;
    private ArrayList<Double> pastLongetudes;
    private double currentLatitude;
    private double currentLongetude;

    public static synchronized GpsPosition getInstance()
    {
        if (gpsPosition == null)
            gpsPosition = new GpsPosition();
        return gpsPosition;
    }

    GpsPosition()
    {
        pastLatitudes = new ArrayList<>();
        pastLongetudes = new ArrayList<>();
    }

    public ArrayList<Double> getPastLatitudes() {
        return pastLatitudes;
    }

    public ArrayList<Double> getPastLongetudes() {
        return pastLongetudes;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public double getCurrentLongetude() {
        return currentLongetude;
    }

    public void setLongetude(double longe)
    {
        currentLongetude = longe;
        pastLongetudes.add(longe);
    }

    public void setLatitude(double lat)
    {
        currentLatitude = lat;
        pastLatitudes.add(lat);
    }
}
