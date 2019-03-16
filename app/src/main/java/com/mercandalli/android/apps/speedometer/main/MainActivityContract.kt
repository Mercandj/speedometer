package com.mercandalli.android.apps.speedometer.main

interface MainActivityContract {

    interface UserAction {

        fun onCreate()

        fun onDestroy()

        fun onStartClicked(
            startStopButtonText: String
        )
    }

    interface Screen {

        fun setSpeed(text: String)

        fun setStartStopButtonText(text: String)
    }
}
