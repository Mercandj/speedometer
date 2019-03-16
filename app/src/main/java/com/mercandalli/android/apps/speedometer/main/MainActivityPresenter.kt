package com.mercandalli.android.apps.speedometer.main

import com.mercandalli.android.apps.speedometer.permission.PermissionManager
import com.mercandalli.android.apps.speedometer.speed.SpeedManager

class MainActivityPresenter(
    private val screen: MainActivityContract.Screen,
    private val speedManager: SpeedManager,
    private val permissionManager: PermissionManager
) : MainActivityContract.UserAction {

    private val speedListener = createSpeedListener()

    override fun onCreate() {
        speedManager.registerListener(speedListener)
        updateScreen()
    }

    override fun onDestroy() {
        speedManager.unregisterListener(speedListener)
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
        speedManager.start()
        updateScreen()
    }

    private fun updateScreen() {
        val speed = speedManager.getSpeed()
        val speedText = "%.2f km/h".format(speed)
        screen.setSpeed(speedText)
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
}
