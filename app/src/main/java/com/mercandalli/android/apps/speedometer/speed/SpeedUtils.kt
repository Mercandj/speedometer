package com.mercandalli.android.apps.speedometer.speed

import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnit

object SpeedUtils {

    private const val HOUR_MULTIPLIER = 3_600
    private const val UNIT_MULTIPLIERS = 0.001

    fun convertFromMeterPerSecond(
        speed: Double,
        toUnit: SpeedUnit
    ): Double {
        return when (toUnit) {
            SpeedUnit.KPH -> speed * 3.6
            SpeedUnit.MPH -> speed * 2.23694
            SpeedUnit.MS -> speed
            SpeedUnit.PACE -> {
                val kmh = convertFromMeterPerSecondToHmh(speed)
                (60f / kmh)
            }
        }
    }

    fun convertFromMeterPerSecondToHmh(speed: Double): Double {
        return speed * HOUR_MULTIPLIER * UNIT_MULTIPLIERS
    }
}
