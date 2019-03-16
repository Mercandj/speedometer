package com.mercandalli.android.apps.speedometer.gps

interface GpsManager {

    fun getGPSCallback(): GpsCallback

    fun setGPSCallback(
        gpsCallback: GpsCallback
    )

    fun startListening()

    fun stopListening()

    fun isListening(): Boolean
}
