package com.mercandalli.android.apps.speedometer.main

import com.mercandalli.android.apps.speedometer.permission.PermissionManager
import com.mercandalli.android.apps.speedometer.speed.SpeedManager
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnit
import com.mercandalli.android.apps.speedometer.speed_unit.SpeedUnitManager
import com.mercandalli.android.apps.speedometer.theme.ThemeManager

class MainActivityPresenter(
    private val screen: MainActivityContract.Screen,
    private val permissionManager: PermissionManager,
    private val speedManager: SpeedManager,
    private val speedUnitManager: SpeedUnitManager,
    private val themeManager: ThemeManager
) : MainActivityContract.UserAction {

    private val speedListener = createSpeedListener()
    private val themeListener = createThemeListener()

    override fun onCreate() {
        speedManager.registerListener(speedListener)
        themeManager.registerThemeListener(themeListener)
        updateScreen()
    }

    override fun onDestroy() {
        speedManager.unregisterListener(speedListener)
        themeManager.unregisterThemeListener(themeListener)
    }

    override fun onMoreViewClicked() {
        val themeView = themeManager.getThemeView()
        val newThemeView = when (themeView) {
            ThemeManager.ThemeView.Tesla -> ThemeManager.ThemeView.Segment
            ThemeManager.ThemeView.Segment -> ThemeManager.ThemeView.Google
            ThemeManager.ThemeView.Google -> ThemeManager.ThemeView.Tesla
        }
        themeManager.setThemeView(newThemeView)
    }

    override fun onMoreThemeClicked() {
        val darkEnable = themeManager.isDarkEnable()
        themeManager.setDarkEnable(
            !darkEnable
        )
    }

    override fun onSpeedUnitClicked() {
        val speedUnit = speedUnitManager.getSpeedUnit()
        val newSpeedUnitManager = when (speedUnit) {
            SpeedUnit.KPH -> SpeedUnit.MPH
            SpeedUnit.MPH -> SpeedUnit.MS
            SpeedUnit.MS -> SpeedUnit.PACE
            SpeedUnit.PACE -> SpeedUnit.KPH
        }
        speedUnitManager.setSpeedUnit(newSpeedUnitManager)
    }

    private fun updateScreen() {
        val themeView = themeManager.getThemeView()
        when (themeView) {
            ThemeManager.ThemeView.Tesla -> screen.showTeslaSpeedView()
            ThemeManager.ThemeView.Segment -> screen.showSegmentSpeedView()
            ThemeManager.ThemeView.Google -> screen.showGoogleSpeedView()
        }
        val theme = themeManager.getTheme()
        screen.setWindowBackgroundColorRes(theme.windowBackgroundColorRes)
    }

    private fun createSpeedListener() = object : SpeedManager.Listener {
        override fun onSpeedChanged() {
            updateScreen()
        }
    }

    private fun createThemeListener() = object : ThemeManager.ThemeListener {

        override fun onThemeChanged() {
            updateScreen()
        }

        override fun onThemeViewChanged() {
            updateScreen()
        }
    }
}
