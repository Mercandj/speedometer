package com.mercandalli.android.apps.speedometer.location

import android.content.Context

class LocationModule(
    private val context: Context
) {

    fun createLocationManager(): LocationManager {
        return LocationManagerImpl(
            context
        )
    }
}
