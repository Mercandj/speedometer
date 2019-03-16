package com.mercandalli.android.apps.speedometer.location

interface LocationManager {

    fun isLocationEnabledOnDevice(): Boolean

    fun enableDeviceLocation()
}
