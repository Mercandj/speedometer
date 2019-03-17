package com.mercandalli.android.apps.speedometer.speed

import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnit
import org.junit.Assert
import org.junit.Test

class SpeedUtilsTest {

    @Test
    fun convertFromMeterPerSecondToPace() {
        // Given
        val speedMs = 60.0 / 1000.0

        // When
        val pace = SpeedUtils.convertFromMeterPerSecond(
            speedMs,
            SpeedUnit.PACE
        )

        // Then
        Assert.assertTrue(
            (1 - pace) < 0.000001
        )
    }

    @Test
    fun convertOneMeterPerSecondToPace() {
        // Given
        val speedMs = 1.0

        // When
        val pace = SpeedUtils.convertFromMeterPerSecond(
            speedMs,
            SpeedUnit.PACE
        )

        // Then
        Assert.assertTrue(
            (0.06 - pace) < 0.000001
        )
    }
}
