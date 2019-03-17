package com.mercandalli.android.apps.speedometer.speed_view_segment

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.mercandalli.android.apps.speedometer.R
import com.mercandalli.android.apps.speedometer.main.ApplicationGraph
import com.mercandalli.android.apps.speedometer.speed_view.SpeedView

// https://www.keshikan.net/fonts-e.html
class SpeedSegmentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SpeedView(context, attrs, defStyleAttr) {

    private val view = View.inflate(context, R.layout.view_speed_segment, this)
    private val speed: TextView = view.findViewById(R.id.view_speed_segment_speed)
    private val speedOff: TextView = view.findViewById(R.id.view_speed_segment_speed_off)
    private val speedUnit: TextView = view.findViewById(R.id.view_speed_segment_speed_unit)
    private val startStopButton: TextView = view.findViewById(R.id.view_speed_segment_start)
    private val title: TextView = view.findViewById(R.id.view_speed_segment_title)
    private val more: ImageView = view.findViewById(R.id.view_speed_segment_more)
    private val userAction = createUserAction()

    init {
        startStopButton.setOnClickListener {
            userAction.onStartClicked(
                startStopButton.text.toString()
            )
        }
        more.setOnClickListener {
            notifyOnMoreClicked(it)
        }
        speedUnit.setOnClickListener {
            notifyOnSpeedUnitClicked(it)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        userAction.onAttached()
    }

    override fun onDetachedFromWindow() {
        userAction.onDetached()
        super.onDetachedFromWindow()
    }

    private fun createScreen() = object : SpeedSegmentViewContract.Screen {

        override fun setSpeedText(
            text: String
        ) {
            speed.text = text
        }

        override fun setSpeedUnitText(text: String) {
            speedUnit.text = text
        }

        override fun setStartStopButtonText(text: String) {
            startStopButton.text = text
        }

        override fun setTextSecondaryColorRes(
            @ColorRes colorRes: Int
        ) {
            val color = ContextCompat.getColor(context, colorRes)
            speed.setTextColor(color)
            speedUnit.setTextColor(color)
            startStopButton.setTextColor(color)
            title.setTextColor(color)
        }

        override fun setTextThirdColorRes(
            @ColorRes colorRes: Int
        ) {
            val color = ContextCompat.getColor(context, colorRes)
            speedOff.setTextColor(color)
        }
    }

    private fun createUserAction(): SpeedSegmentViewContract.UserAction {
        if (isInEditMode) {
            return object : SpeedSegmentViewContract.UserAction {
                override fun onAttached() {}
                override fun onDetached() {}
                override fun onStartClicked(startStopButtonText: String) {}
            }
        }
        val screen = createScreen()
        val locationManager = ApplicationGraph.getLocationManager()
        val permissionManager = ApplicationGraph.getPermissionManager()
        val speedManager = ApplicationGraph.getSpeedManager()
        val speedUnitManager = ApplicationGraph.getSpeedUnitManager()
        val themeManager = ApplicationGraph.getThemeManager()
        return SpeedSegmentViewPresenter(
            screen,
            locationManager,
            permissionManager,
            speedManager,
            speedUnitManager,
            themeManager
        )
    }
}
