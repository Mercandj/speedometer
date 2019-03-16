package com.mercandalli.android.apps.speedometer.speed_view_tesla

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mercandalli.android.apps.speedometer.R
import com.mercandalli.android.apps.speedometer.main.ApplicationGraph
import com.mercandalli.android.apps.speedometer.speed_view.SpeedView

class SpeedTeslaView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SpeedView(context, attrs, defStyleAttr) {

    private val view = View.inflate(context, R.layout.view_speed_tesla, this)
    private val speed: TextView = view.findViewById(R.id.view_speed_tesla_speed)
    private val speedUnit: TextView = view.findViewById(R.id.view_speed_tesla_speed_unit)
    private val fab: FloatingActionButton = view.findViewById(R.id.view_speed_tesla_fab)
    private val more: ImageView = view.findViewById(R.id.view_speed_tesla_more)
    private val userAction = createUserAction()

    init {
        fab.setOnClickListener {
            userAction.onFabClicked()
        }
        more.setOnClickListener {
            notifyOnMoreClicked(it)
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

    private fun createScreen() = object : SpeedTeslaViewContract.Screen {

        override fun setSpeedText(
            text: String
        ) {
            speed.text = text
        }

        override fun setSpeedUnitText(text: String) {
            speedUnit.text = text
        }

        override fun setStartStopButtonText(drawableRes: Int) {
            fab.setImageResource(drawableRes)
        }
    }

    private fun createUserAction(): SpeedTeslaViewContract.UserAction {
        if (isInEditMode) {
            return object : SpeedTeslaViewContract.UserAction {
                override fun onAttached() {}
                override fun onDetached() {}
                override fun onFabClicked() {}
            }
        }
        val screen = createScreen()
        val locationManager = ApplicationGraph.getLocationManager()
        val permissionManager = ApplicationGraph.getPermissionManager()
        val speedManager = ApplicationGraph.getSpeedManager()
        return SpeedTeslaViewPresenter(
            screen,
            locationManager,
            permissionManager,
            speedManager
        )
    }
}
