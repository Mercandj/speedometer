package com.mercandalli.android.apps.speedometer.speed_view_tesla

import com.mercandalli.android.apps.speedometer.R
import com.mercandalli.android.apps.speedometer.location.LocationManager
import com.mercandalli.android.apps.speedometer.permission.PermissionManager
import com.mercandalli.android.apps.speedometer.speed.SpeedManager
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnit
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnitManager

class SpeedTeslaViewPresenter(
    private val screen: SpeedTeslaViewContract.Screen,
    private val locationManager: LocationManager,
    private val permissionManager: PermissionManager,
    private val speedManager: SpeedManager,
    private val speedUnitManager: SpeedUnitManager
) : SpeedTeslaViewContract.UserAction {

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
            SpeedUnit.MPH -> speedManager.getSpeed().toInt()
        }
        val speedText = speed.toString()
        screen.setSpeedText(
            speedText
        )
        screen.setSpeedUnitText(
            when (speedUnit) {
                SpeedUnit.KPH -> "km/h"
                SpeedUnit.MPH -> "mph"
            }
        )
        val fabDrawableRes = if (speedManager.isStarted()) {
            R.drawable.ic_stop_white_24dp
        } else {
            R.drawable.ic_play_arrow_white_24dp
        }
        screen.setStartStopButtonText(fabDrawableRes)
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
