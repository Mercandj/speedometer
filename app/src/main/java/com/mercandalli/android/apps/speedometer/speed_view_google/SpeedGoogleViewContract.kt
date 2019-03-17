package com.mercandalli.android.apps.speedometer.speed_view_google

import androidx.annotation.ColorRes

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
            textLastDigits: String,
            @ColorRes textThirdColorRes: Int
        )

        fun setSpeedUnitText(text: String)

        fun setStartStopButtonText(text: String)

        fun setTextSecondaryColorRes(
            @ColorRes colorRes: Int
        )
    }
}
