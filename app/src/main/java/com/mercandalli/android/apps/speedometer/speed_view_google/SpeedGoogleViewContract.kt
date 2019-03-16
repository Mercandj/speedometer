package com.mercandalli.android.apps.speedometer.speed_view_google

interface SpeedGoogleViewContract {

    interface UserAction {

        fun onAttached()

        fun onDetached()

        fun onStartClicked(
            startStopButtonText: String
        )
    }

    interface Screen {

        fun setSpeedText(
            textFirstDigits: String,
            textLastDigits: String
        )

        fun setSpeedUnitText(text: String)

        fun setStartStopButtonText(text: String)
    }
}
