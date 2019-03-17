package com.mercandalli.android.apps.speedometer.speed

interface SpeedManager {

    fun start()

    fun stop()

    fun isStarted(): Boolean

    fun getSpeed(): Double

    fun getSpeedMph(): Double

    fun getSpeedKmh(): Double

    fun getSpeedPace(): Double

    fun registerListener(listener: Listener)

    fun unregisterListener(listener: Listener)

    interface Listener {

        fun onSpeedChanged()
    }
}
