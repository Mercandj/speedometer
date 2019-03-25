package com.mercandalli.android.apps.speedometer.speed

import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnit
import org.junit.Assert
import org.junit.Test

class SpeedUtilsTest {

    @Test
    fun convertFiveMeterPerSecondToPace() {
        // Given
        val speedMs = 4.0 // 14.4 km/h

        // When
        val pace = SpeedUtils.convertFromMeterPerSecond(
            speedMs,
            SpeedUnit.PACE
        )

        // Then
        Assert.assertTrue(
            "Pace $pace",
            pace < 4.2 && pace > 4.1
        )
    }

    @Test
    fun convertTenHmhToPace() {
        // Given
        val speedMs = 2.7777777777 // 10.0 km/h

        // When
        val pace = SpeedUtils.convertFromMeterPerSecond(
            speedMs,
            SpeedUnit.PACE
        )

        // Then
        Assert.assertTrue(
            "Pace $pace",
            pace < 6.1111 && pace > 5.99999
        )
    }
}
