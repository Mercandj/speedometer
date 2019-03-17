package com.mercandalli.android.apps.speedometer.speed

import android.location.Location
import com.mercandalli.android.apps.speedometer.gps.GpsCallback
import com.mercandalli.android.apps.speedometer.gps.GpsManager
import com.mercandalli.android.apps.speedometer.main_thread.MainThreadPost
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnit

class SpeedManagerImpl(
    private val gpsManager: GpsManager,
    private val mainThreadPost: MainThreadPost
) : SpeedManager {

    private val listeners = ArrayList<SpeedManager.Listener>()
    private var speed = 0.0
    private var speedKmh = 0.0
    private var speedMph = 0.0
    private var speedPace = 0.0

    init {
        gpsManager.setGPSCallback(object : GpsCallback {
            override fun onGPSUpdate(location: Location?) {
                if (location == null) {
                    resetSpeed()
                    return
                }
                speed = location.speed.toDouble()
                speedKmh = convertSpeedHmh(speed)
                speedMph = convertSpeedMph(speed)
                speedPace = convertSpeedPace(speed)
                notifyListeners()
            }
        })
    }

    override fun start() {
        gpsManager.startListening()
    }

    override fun stop() {
        gpsManager.stopListening()
        resetSpeed()
    }

    override fun isStarted(): Boolean {
        return gpsManager.isListening()
    }

    override fun getSpeed() = speed

    override fun getSpeedMph() = speedMph

    override fun getSpeedKmh() = speedKmh

    override fun getSpeedPace() = speedPace

    override fun registerListener(listener: SpeedManager.Listener) {
        if (listeners.contains(listener)) {
            return
        }
        listeners.add(listener)
    }

    override fun unregisterListener(listener: SpeedManager.Listener) {
        listeners.remove(listener)
    }

    private fun resetSpeed() {
        speed = 0.0
        speedKmh = 0.0
        speedMph = 0.0
        speedPace = 0.0
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

        private fun convertSpeedHmh(speed: Double): Double {
            return speed * HOUR_MULTIPLIER * UNIT_MULTIPLIERS
        }

        private fun convertSpeedMph(speed: Double): Double {
            return speed * 2.23694
        }

        private fun convertSpeedPace(speed: Double): Double {
            return SpeedUtils.convertFromMeterPerSecond(
                speed,
                SpeedUnit.PACE
            )
        }
    }
}
