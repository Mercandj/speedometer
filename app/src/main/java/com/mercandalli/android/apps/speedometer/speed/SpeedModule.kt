package com.mercandalli.android.apps.speedometer.speed

import com.mercandalli.android.apps.speedometer.main.ApplicationGraph

class SpeedModule {

    fun createSpeedManager(): SpeedManager {
        val gpsManager = ApplicationGraph.getGpsManager()
        val mainThreadPost = ApplicationGraph.getMainThreadPost()
        return SpeedManagerImpl(
            gpsManager,
            mainThreadPost
        )
    }
}
