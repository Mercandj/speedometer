package com.mercandalli.android.apps.speedometer.speed_unit

import android.content.Context

class SpeedUnitModule(
    private val context: Context
) {

    fun createSpeedUnitManager(): SpeedUnitManager {
        val sharedPreferences = context.getSharedPreferences(
            SpeedUnitManagerImpl.PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        return SpeedUnitManagerImpl(
            sharedPreferences
        )
    }
}
