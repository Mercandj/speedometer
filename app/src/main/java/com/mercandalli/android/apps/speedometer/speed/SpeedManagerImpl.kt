package com.mercandalli.android.apps.speedometer.speed

import android.location.Location
import com.mercandalli.android.apps.speedometer.gps.GpsCallback
import com.mercandalli.android.apps.speedometer.gps.GpsManager
import com.mercandalli.android.apps.speedometer.main_thread.MainThreadPost

class SpeedManagerImpl(
    private val gpsManager: GpsManager,
    private val mainThreadPost: MainThreadPost
) : SpeedManager {

    private val listeners = ArrayList<SpeedManager.Listener>()
    private var speed = 0.0
    private var speedKmh = 0.0

    init {
        gpsManager.setGPSCallback(object : GpsCallback {
            override fun onGPSUpdate(location: Location?) {
                if (location == null) {
                    speed = 0.0
                    speedKmh = 0.0
                    return
                }
                speed = location.speed.toDouble()
                speedKmh = convertSpeed(speed)
                notifyListeners()
            }
        })
    }

    override fun start() {
        gpsManager.startListening()
    }

    override fun stop() {
        gpsManager.stopListening()
        speed = 0.0
        speedKmh = 0.0
    }

    override fun isStarted(): Boolean {
        return gpsManager.isListening()
    }

    override fun getSpeed() = speed

    override fun getSpeedKmh() = speedKmh

    override fun registerListener(listener: SpeedManager.Listener) {
        if (listeners.contains(listener)) {
            return
        }
        listeners.add(listener)
    }

    override fun unregisterListener(listener: SpeedManager.Listener) {
        listeners.remove(listener)
    }

    private fun notifyListeners() {
        if (!mainThreadPost.isOnMainThread()) {
            mainThreadPost.post(Runnable {
                notifyListeners()
            })
            return
        }
        for (listener in listeners) {
            listener.onSpeedChanged()
        }
    }

    companion object {

        private const val HOUR_MULTIPLIER = 3600
        private const val UNIT_MULTIPLIERS = 0.001

        private fun convertSpeed(speed: Double): Double {
            return speed * HOUR_MULTIPLIER * UNIT_MULTIPLIERS
        }
    }
}
