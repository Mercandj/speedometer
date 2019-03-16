package com.mercandalli.android.apps.speedometer.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// https://github.com/pintukumarpatil/Speedometer
public class GpsManagerImpl implements GpsManager {

    private static final int gpsMinTime = 500;
    private static final int gpsMinDistance = 0;

    private static LocationManager locationManager = null;
    private static LocationListener locationListener = null;
    private static GpsCallback gpsCallback = null;
    Context context;

    public GpsManagerImpl(Context context) {
        this.context = context;
        GpsManagerImpl.locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                if (GpsManagerImpl.gpsCallback != null) {
                    GpsManagerImpl.gpsCallback.onGPSUpdate(location);
                }
            }

            @Override
            public void onProviderDisabled(final String provider) {
            }

            @Override
            public void onProviderEnabled(final String provider) {
            }

            @Override
            public void onStatusChanged(final String provider, final int status, final Bundle extras) {
            }
        };
    }

    @NotNull
    public GpsCallback getGPSCallback() {
        return GpsManagerImpl.gpsCallback;
    }

    public void setGPSCallback(@NotNull final GpsCallback gpsCallback) {
        GpsManagerImpl.gpsCallback = gpsCallback;
    }

    public void startListening() {
        if (isListening()) {
            return;
        }

        if (GpsManagerImpl.locationManager == null) {
            GpsManagerImpl.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        final Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        final String bestProvider = GpsManagerImpl.locationManager.getBestProvider(criteria, true);

        if (bestProvider != null && bestProvider.length() > 0) {
            if (
                    ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            GpsManagerImpl.locationManager.requestLocationUpdates(
                    bestProvider,
                    GpsManagerImpl.gpsMinTime,
                    GpsManagerImpl.gpsMinDistance,
                    GpsManagerImpl.locationListener
            );
        } else {
            final List<String> providers = GpsManagerImpl.locationManager.getProviders(true);

            for (final String provider : providers) {
                GpsManagerImpl.locationManager.requestLocationUpdates(provider, GpsManagerImpl.gpsMinTime,
                        GpsManagerImpl.gpsMinDistance, GpsManagerImpl.locationListener);
            }
        }
    }

    public void stopListening() {
        try {
            if (GpsManagerImpl.locationManager != null && GpsManagerImpl.locationListener != null) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                GpsManagerImpl.locationManager.removeUpdates(GpsManagerImpl.locationListener);
            }

            GpsManagerImpl.locationManager = null;
        } catch (final Exception ex) {

        }
    }

    @Override
    public boolean isListening() {
        return GpsManagerImpl.locationManager != null;
    }
}