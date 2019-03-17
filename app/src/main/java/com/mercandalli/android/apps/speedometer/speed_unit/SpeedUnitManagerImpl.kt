package com.mercandalli.android.apps.speedometer.speed_unit

import android.content.SharedPreferences

class SpeedUnitManagerImpl(
    private val sharedPreferences: SharedPreferences
) : SpeedUnitManager {

    private val speedUnitListeners = ArrayList<SpeedUnitManager.SpeedUnitListener>()
    private var speedUnit = SpeedUnit.KPH

    init {
        speedUnit = load()
    }

    override fun getSpeedUnit(): SpeedUnit {
        return speedUnit
    }

    override fun setSpeedUnit(speedUnit: SpeedUnit) {
        if (this.speedUnit == speedUnit) {
            return
        }
        this.speedUnit = speedUnit
        save(speedUnit)
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

    private fun load(): SpeedUnit {
        val speedUnitString = sharedPreferences.getString(
            "speed-unit",
            speedUnitToString(speedUnit)
        )!!
        return stringToSpeedUnit(speedUnitString)
    }

    private fun save(speedUnit: SpeedUnit) {
        sharedPreferences.edit()
            .putString("speed-unit", speedUnitToString(speedUnit))
            .apply()
    }

    companion object {
        @JvmStatic
        val PREFERENCE_NAME = "SpeedUnitManager"

        private fun speedUnitToString(
            speedUnit: SpeedUnit
        ): String {
            return when (speedUnit) {
                SpeedUnit.MPH -> "mph"
                SpeedUnit.KPH -> "kph"
                SpeedUnit.MS -> "ms"
                SpeedUnit.PACE -> "pace"
            }
        }

        private fun stringToSpeedUnit(
            speedUnitString: String
        ): SpeedUnit {
            return when (speedUnitString) {
                "mph" -> SpeedUnit.MPH
                "kpm" -> SpeedUnit.KPH
                "ms" -> SpeedUnit.MS
                "pace" -> SpeedUnit.PACE
                else -> SpeedUnit.KPH
            }
        }
    }
}
