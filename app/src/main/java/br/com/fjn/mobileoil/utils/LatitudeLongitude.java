package br.com.fjn.mobileoil.utils;

import android.content.Context;

/**
 * Created by unobre on 29/08/2015.
 */
public class LatitudeLongitude {

    private static final String TAG = "MO_LATITUDE_CLASS";
    private static String latitudeLongitude = null;

    private Context context;

    public LatitudeLongitude(Context context) {
        this.context = context;
    }

    public static String getLatitudeLongitude() {
        return latitudeLongitude;
    }

    public static void setLatitudeLongitude(String latitudeLongitude) {
        LatitudeLongitude.latitudeLongitude = latitudeLongitude;
    }
}
