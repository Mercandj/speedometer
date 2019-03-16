package com.mercandalli.android.apps.speedometer.theme

interface ThemeManager {

    fun getTheme(): Theme

    fun setDarkEnable(enable: Boolean)

    fun getThemeView(): ThemeView

    fun setThemeView(themeView: ThemeView)

    fun isDarkEnable(): Boolean

    fun registerThemeListener(listener: ThemeListener)

    fun unregisterThemeListener(listener: ThemeListener)

    interface ThemeListener {

        fun onThemeChanged()

        fun onThemeViewChanged()
    }

    enum class ThemeView {
        Tesla,
        Segment,
        Google
    }
}
