package com.mercandalli.android.apps.speedometer.main

interface MainActivityContract {

    interface UserAction {

        fun onCreate()

        fun onDestroy()

        fun onMoreClicked()

        fun onSpeedUnitClicked()
    }

    interface Screen {

        fun showTeslaSpeedView()

        fun showSegmentSpeedView()

        fun showGoogleSpeedView()
    }
}
