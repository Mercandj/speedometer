package com.mercandalli.android.apps.speedometer.speed_view_segment

import com.mercandalli.android.apps.speedometer.location.LocationManager
import com.mercandalli.android.apps.speedometer.permission.PermissionManager
import com.mercandalli.android.apps.speedometer.speed.SpeedManager
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnit
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnitManager

class SpeedSegmentViewPresenter(
    private val screen: SpeedSegmentViewContract.Screen,
    private val locationManager: LocationManager,
    private val permissionManager: PermissionManager,
    private val speedManager: SpeedManager,
    private val speedUnitManager: SpeedUnitManager
) : SpeedSegmentViewContract.UserAction {

    private val speedListener = createSpeedListener()
    private val speedUnitListener = createSpeedUnitListener()

    override fun onAttached() {
        speedManager.registerListener(speedListener)
        speedUnitManager.registerSpeedUnitListener(speedUnitListener)
        updateScreen()
    }

    override fun onDetached() {
        speedManager.unregisterListener(speedListener)
        speedUnitManager.unregisterSpeedUnitListener(speedUnitListener)
    }

    override fun onStartClicked(
        startStopButtonText: String
    ) {
        if (startStopButtonText == "STOP") {
            speedManager.stop()
            updateScreen()
            return
        }
        val gpsPermission = permissionManager.hasGpsPermission()
        if (!gpsPermission) {
            permissionManager.requestGpsPermission()
            return
        }
        if (!locationManager.isLocationEnabledOnDevice()) {
            locationManager.enableDeviceLocation()
            return
        }
        speedManager.start()
        updateScreen()
    }

    private fun updateScreen() {
        val speedUnit = speedUnitManager.getSpeedUnit()
        val speed = when (speedUnit) {
            SpeedUnit.KPH -> speedManager.getSpeedKmh().toInt()
            SpeedUnit.MPH -> speedManager.getSpeed().toInt()
        }
        val speedText = speed.toString()
        val speedFirstDigit = when {
            speed < 10 -> "00"
            speed < 100 -> "0"
            else -> ""
        }
        screen.setSpeedText(
            speedFirstDigit,
            speedText
        )
        screen.setSpeedUnitText(
            when (speedUnit) {
                SpeedUnit.KPH -> "km/h"
                SpeedUnit.MPH -> "mph"
            }
        )
        val startStopButtonText = if (speedManager.isStarted()) {
            "STOP"
        } else {
            "START"
        }
        screen.setStartStopButtonText(startStopButtonText)
    }

    private fun createSpeedListener() = object : SpeedManager.Listener {
        override fun onSpeedChanged() {
            updateScreen()
        }
    }

    private fun createSpeedUnitListener() = object : SpeedUnitManager.SpeedUnitListener {
        override fun onSpeedUnitChanged() {
            updateScreen()
        }
    }
}
