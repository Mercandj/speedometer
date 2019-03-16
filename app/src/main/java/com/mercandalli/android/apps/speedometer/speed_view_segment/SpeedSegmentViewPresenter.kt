package com.mercandalli.android.apps.speedometer.speed_view_segment

import com.mercandalli.android.apps.speedometer.permission.PermissionManager
import com.mercandalli.android.apps.speedometer.speed.SpeedManager

class SpeedSegmentViewPresenter(
    private val screen: SpeedSegmentViewContract.Screen,
    private val speedManager: SpeedManager,
    private val permissionManager: PermissionManager
) : SpeedSegmentViewContract.UserAction {

    private val speedListener = createSpeedListener()

    override fun onAttached() {
        speedManager.registerListener(speedListener)
        updateScreen()
    }

    override fun onDetached() {
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
        val speed = speedManager.getSpeedKmh().toInt()
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
        screen.setSpeedUnitText("km/h")
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
