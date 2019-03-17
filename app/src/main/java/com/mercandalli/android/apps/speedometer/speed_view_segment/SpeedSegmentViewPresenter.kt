package com.mercandalli.android.apps.speedometer.speed_view_segment

import com.mercandalli.android.apps.speedometer.location.LocationManager
import com.mercandalli.android.apps.speedometer.permission.PermissionManager
import com.mercandalli.android.apps.speedometer.speed.SpeedManager
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnit
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnitManager
import com.mercandalli.android.apps.speedometer.theme.ThemeManager

class SpeedSegmentViewPresenter(
    private val screen: SpeedSegmentViewContract.Screen,
    private val locationManager: LocationManager,
    private val permissionManager: PermissionManager,
    private val speedManager: SpeedManager,
    private val speedUnitManager: SpeedUnitManager,
    private val themeManager: ThemeManager
) : SpeedSegmentViewContract.UserAction {

    private val speedListener = createSpeedListener()
    private val speedUnitListener = createSpeedUnitListener()
    private val themeListener = createThemeListener()

    override fun onAttached() {
        speedManager.registerListener(speedListener)
        speedUnitManager.registerSpeedUnitListener(speedUnitListener)
        themeManager.registerThemeListener(themeListener)
        updateScreen()
    }

    override fun onDetached() {
        speedManager.unregisterListener(speedListener)
        speedUnitManager.unregisterSpeedUnitListener(speedUnitListener)
        themeManager.unregisterThemeListener(themeListener)
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
        val theme = themeManager.getTheme()
        val speedUnit = speedUnitManager.getSpeedUnit()
        val speed = when (speedUnit) {
            SpeedUnit.KPH -> speedManager.getSpeedKmh().toInt()
            SpeedUnit.MPH -> speedManager.getSpeedMph().toInt()
            SpeedUnit.MS -> speedManager.getSpeed().toInt()
            SpeedUnit.PACE -> speedManager.getSpeedPace().toInt()
        }
        val speedText = speed.toString()
        screen.setSpeedText(
            speedText
        )
        screen.setSpeedUnitText(
            when (speedUnit) {
                SpeedUnit.KPH -> "km/h"
                SpeedUnit.MPH -> "mph"
                SpeedUnit.MS -> "m/s"
                SpeedUnit.PACE -> "pace"
            }
        )
        val startStopButtonText = if (speedManager.isStarted()) {
            "STOP"
        } else {
            "START"
        }
        screen.setStartStopButtonText(startStopButtonText)
        screen.setTextSecondaryColorRes(theme.textSecondaryColorRes)
        screen.setTextThirdColorRes(theme.textThirdColorRes)
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

    private fun createThemeListener() = object : ThemeManager.ThemeListener {
        override fun onThemeChanged() {
            updateScreen()
        }

        override fun onThemeViewChanged() {
            updateScreen()
        }
    }
}
