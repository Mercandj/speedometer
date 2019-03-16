package com.mercandalli.android.apps.speedometer.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings

class LocationManagerImpl(
    private val context: Context
) : LocationManager {

    @SuppressLint("ServiceCast")
    var lm = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager

    override fun isLocationEnabledOnDevice(): Boolean {
        return lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
    }

    override fun enableDeviceLocation() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        if (context !is Activity) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(intent)
    }
}
