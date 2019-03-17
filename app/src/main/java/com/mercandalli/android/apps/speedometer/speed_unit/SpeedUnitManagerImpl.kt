package com.mercandalli.android.apps.speedometer.speed_unit

class SpeedUnitManagerImpl : SpeedUnitManager {

    private val speedUnitListeners = ArrayList<SpeedUnitManager.SpeedUnitListener>()
    private var speedUnit = SpeedUnit.KPH

    override fun getSpeedUnit(): SpeedUnit {
        return speedUnit
    }

    override fun setSpeedUnit(speedUnit: SpeedUnit) {
        if (this.speedUnit == speedUnit) {
            return
        }
        this.speedUnit = speedUnit
        for (listener in speedUnitListeners) {
            listener.onSpeedUnitChanged()
        }
    }

    override fun registerSpeedUnitListener(listener: SpeedUnitManager.SpeedUnitListener) {
        if (speedUnitListeners.contains(listener)) {
            return
        }
        speedUnitListeners.add(listener)
    }

    override fun unregisterSpeedUnitListener(listener: SpeedUnitManager.SpeedUnitListener) {
        speedUnitListeners.remove(listener)
    }
}
