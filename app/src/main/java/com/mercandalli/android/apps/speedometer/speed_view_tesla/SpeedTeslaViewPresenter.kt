package com.mercandalli.android.apps.speedometer.speed_view_tesla

import com.mercandalli.android.apps.speedometer.R
import com.mercandalli.android.apps.speedometer.location.LocationManager
import com.mercandalli.android.apps.speedometer.permission.PermissionManager
import com.mercandalli.android.apps.speedometer.speed.SpeedManager
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnit
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnitManager
import com.mercandalli.android.apps.speedometer.theme.ThemeManager

class SpeedTeslaViewPresenter(
    private val screen: SpeedTeslaViewContract.Screen,
    private val locationManager: LocationManager,
    private val permissionManager: PermissionManager,
    private val speedManager: SpeedManager,
    private val speedUnitManager: SpeedUnitManager,
    private val themeManager: ThemeManager
) : SpeedTeslaViewContract.UserAction {

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

    override fun onFabClicked() {
        if (speedManager.isStarted()) {
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
        val fabDrawableRes = if (speedManager.isStarted()) {
            R.drawable.ic_stop_white_24dp
        } else {
            R.drawable.ic_play_arrow_white_24dp
        }
        screen.setStartStopButtonText(fabDrawableRes)
        val theme = themeManager.getTheme()
        screen.setTextSecondaryColorRes(theme.textSecondaryColorRes)
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
