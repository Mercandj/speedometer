package com.mercandalli.android.apps.speedometer.gps

import android.content.Context

class GpsModule(
    private val context: Context
) {

    fun createGpsManager(): GpsManager {
        return GpsManagerImpl(
            context
        )
    }
}
