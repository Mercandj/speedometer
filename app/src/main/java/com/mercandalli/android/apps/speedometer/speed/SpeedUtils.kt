package com.mercandalli.android.apps.speedometer.speed

import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnit

object SpeedUtils {

    fun convertFromMeterPerSecond(
        speed: Double,
        toUnit: SpeedUnit
    ): Double {
        return when (toUnit) {
            SpeedUnit.KPH -> speed * 3.6
            SpeedUnit.MPH -> speed * 2.23694
            SpeedUnit.MS -> speed
            SpeedUnit.PACE -> (60.0 / (speed * 1000.0))
        }
    }
}
