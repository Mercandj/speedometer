package com.mercandalli.android.apps.speedometer.speed_view_segment

import androidx.annotation.ColorRes

interface SpeedSegmentViewContract {

    interface UserAction {

        fun onAttached()

        fun onDetached()

        fun onStartClicked(
            startStopButtonText: String
        )
    }

    interface Screen {

        fun setSpeedText(
            text: String
        )

        fun setSpeedUnitText(
            text: String
        )

        fun setStartStopButtonText(
            text: String
        )

        fun setTextSecondaryColorRes(
            @ColorRes colorRes: Int
        )

        fun setTextThirdColorRes(
            @ColorRes colorRes: Int
        )
    }
}
