package com.mercandalli.android.apps.speedometer.main

import androidx.annotation.ColorRes

interface MainActivityContract {

    interface UserAction {

        fun onCreate()

        fun onDestroy()

        fun onMoreViewClicked()

        fun onMoreThemeClicked()

        fun onSpeedUnitClicked()
    }

    interface Screen {

        fun showTeslaSpeedView()

        fun showSegmentSpeedView()

        fun showGoogleSpeedView()

        fun setWindowBackgroundColorRes(
            @ColorRes colorRes: Int
        )
    }
}
