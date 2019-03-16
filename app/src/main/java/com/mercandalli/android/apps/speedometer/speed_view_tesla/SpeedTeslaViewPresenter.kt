package com.mercandalli.android.apps.speedometer.speed_view_tesla

import com.mercandalli.android.apps.speedometer.R
import com.mercandalli.android.apps.speedometer.permission.PermissionManager
import com.mercandalli.android.apps.speedometer.speed.SpeedManager

class SpeedTeslaViewPresenter(
    private val screen: SpeedTeslaViewContract.Screen,
    private val speedManager: SpeedManager,
    private val permissionManager: PermissionManager
) : SpeedTeslaViewContract.UserAction {

    private val speedListener = createSpeedListener()

    override fun onAttached() {
        speedManager.registerListener(speedListener)
        updateScreen()
    }

    override fun onDetached() {
        speedManager.unregisterListener(speedListener)
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
        speedManager.start()
        updateScreen()
    }

    private fun updateScreen() {
        val speed = speedManager.getSpeedKmh().toInt()
        val speedText = speed.toString()
        screen.setSpeedText(
            speedText
        )
        screen.setSpeedUnitText("km/h")
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
}
