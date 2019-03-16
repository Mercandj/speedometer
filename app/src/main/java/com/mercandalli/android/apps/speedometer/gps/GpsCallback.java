package com.mercandalli.android.apps.speedometer.gps;

import android.location.Location;

public interface GpsCallback {

    void onGPSUpdate(Location location);
}
