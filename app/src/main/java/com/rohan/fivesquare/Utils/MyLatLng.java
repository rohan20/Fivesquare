package com.rohan.fivesquare.Utils;

/**
 * Created by Rohan on 25-Jul-16.
 */
public class MyLatLng {

    private double lat;
    private double lng;

    public MyLatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return String.format("%.1f,%.1f", lat, lng);
    }
}
