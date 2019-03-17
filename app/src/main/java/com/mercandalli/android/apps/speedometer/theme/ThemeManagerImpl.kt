package com.mercandalli.android.apps.speedometer.theme

import android.content.SharedPreferences
import java.util.concurrent.CopyOnWriteArrayList

internal class ThemeManagerImpl(
    private val sharedPreferences: SharedPreferences
) : ThemeManager {

    private val lightTheme: Theme = LightTheme()
    private val darkTheme: Theme = DarkTheme()
    private var currentThemeIndex = 0
    private var listeners = CopyOnWriteArrayList<ThemeManager.ThemeListener>()
    private var themeView = ThemeManager.ThemeView.Tesla

    init {
        currentThemeIndex = sharedPreferences.getInt("theme", 0)
        themeView = stringToThemeView(
            sharedPreferences.getString(
                "theme-view",
                themeViewToString(themeView)
            )!!
        )
    }

    override fun getTheme(): Theme = if (currentThemeIndex == 0) lightTheme else darkTheme

    override fun setDarkEnable(enable: Boolean) {
        currentThemeIndex = if (enable) 1 else 0
        sharedPreferences.edit().putInt("theme", currentThemeIndex).apply()
        for (listener in listeners) {
            listener.onThemeChanged()
        }
    }

    override fun getThemeView(): ThemeManager.ThemeView {
        return themeView
    }

    override fun setThemeView(themeView: ThemeManager.ThemeView) {
        if (this.themeView == themeView) {
            return
        }
        this.themeView = themeView
        sharedPreferences.edit()
            .putString(
                "theme-view",
                themeViewToString(themeView)
            )
            .apply()
        for (listener in listeners) {
            listener.onThemeViewChanged()
        }
    }

    override fun isDarkEnable(): Boolean {
        return currentThemeIndex != 0
    }

    override fun registerThemeListener(listener: ThemeManager.ThemeListener) {
        if (listeners.contains(listener)) {
            return
        }
        listeners.add(listener)
    }

    override fun unregisterThemeListener(listener: ThemeManager.ThemeListener) {
        listeners.remove(listener)
    }

    companion object {
        @JvmStatic
        val PREFERENCE_NAME = "ThemeManager"

        private fun themeViewToString(
            themeView: ThemeManager.ThemeView
        ): String {
            return when (themeView) {
                ThemeManager.ThemeView.Tesla -> "tesla"
                ThemeManager.ThemeView.Segment -> "segment"
                ThemeManager.ThemeView.Google -> "google"
            }
        }

        private fun stringToThemeView(
            themeViewString: String
        ): ThemeManager.ThemeView {
            return when (themeViewString) {
                "tesla" -> ThemeManager.ThemeView.Tesla
                "segment" -> ThemeManager.ThemeView.Segment
                "google" -> ThemeManager.ThemeView.Google
                else -> ThemeManager.ThemeView.Tesla
            }
        }
    }
}
