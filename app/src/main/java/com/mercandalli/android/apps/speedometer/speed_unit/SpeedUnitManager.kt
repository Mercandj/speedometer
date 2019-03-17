package com.mercandalli.android.apps.speedometer.speed_unit

interface SpeedUnitManager {

    fun getSpeedUnit(): SpeedUnit

    fun setSpeedUnit(
        speedUnit: SpeedUnit
    )

    fun registerSpeedUnitListener(
        listener: SpeedUnitManager.SpeedUnitListener
    )

    fun unregisterSpeedUnitListener(
        listener: SpeedUnitManager.SpeedUnitListener
    )

    interface SpeedUnitListener {

        fun onSpeedUnitChanged()
    }
}
