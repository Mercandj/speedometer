package com.mercandalli.android.apps.speedometer.speed_unit

class SpeedUnitModule {

    fun createSpeedUnitManager(): SpeedUnitManager {
        return SpeedUnitManagerImpl()
    }
}
