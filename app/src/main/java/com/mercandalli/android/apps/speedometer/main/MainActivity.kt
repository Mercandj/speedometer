package com.mercandalli.android.apps.speedometer.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.mercandalli.android.apps.speedometer.R
import com.mercandalli.android.apps.speedometer.activity.ActivityExtension.bind
import com.mercandalli.android.apps.speedometer.speed_view.SpeedView
import com.mercandalli.android.apps.speedometer.speed_view_google.SpeedGoogleView
import com.mercandalli.android.apps.speedometer.speed_view_segment.SpeedSegmentView
import com.mercandalli.android.apps.speedometer.speed_view_tesla.SpeedTeslaView

class MainActivity : AppCompatActivity() {

    private val speedViewContainer: FrameLayout by bind(R.id.activity_main_speed_view_container)
    private val onMoreClickedListener = createOnMoreClickedListener()
    private val userAction = createUserAction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userAction.onCreate()
    }

    override fun onDestroy() {
        userAction.onDestroy()
        super.onDestroy()
    }

    private fun isCurrentSpeedViewTesla(): Boolean {
        if (speedViewContainer.childCount == 0) {
            return false
        }
        val currentSpeedView = speedViewContainer.getChildAt(0)
        return currentSpeedView is SpeedTeslaView
    }

    private fun isCurrentSpeedViewSegment(): Boolean {
        if (speedViewContainer.childCount == 0) {
            return false
        }
        val currentSpeedView = speedViewContainer.getChildAt(0)
        return currentSpeedView is SpeedSegmentView
    }

    private fun isCurrentSpeedViewGoogle(): Boolean {
        if (speedViewContainer.childCount == 0) {
            return false
        }
        val currentSpeedView = speedViewContainer.getChildAt(0)
        return currentSpeedView is SpeedGoogleView
    }

    private fun clearSpeedViewContainer() {
        if (speedViewContainer.childCount == 0) {
            return
        }
        val currentSpeedView = speedViewContainer.getChildAt(0) as SpeedView
        currentSpeedView.setOnMoreClickedListener(null)
        speedViewContainer.removeAllViews()
    }

    private fun createOnMoreClickedListener() = object : SpeedView.OnMoreClickedListener {
        override fun onMoreClicked(view: View) {
            userAction.onMoreClicked()
        }
    }

    private fun createScreen() = object : MainActivityContract.Screen {
        override fun showTeslaSpeedView() {
            if (isCurrentSpeedViewTesla()) {
                return
            }
            clearSpeedViewContainer()
            val speedView = SpeedTeslaView(this@MainActivity)
            speedView.setOnMoreClickedListener(onMoreClickedListener)
            speedViewContainer.addView(speedView)
        }

        override fun showSegmentSpeedView() {
            if (isCurrentSpeedViewSegment()) {
                return
            }
            clearSpeedViewContainer()
            val speedView = SpeedSegmentView(this@MainActivity)
            speedView.setOnMoreClickedListener(onMoreClickedListener)
            speedViewContainer.addView(speedView)

        }

        override fun showGoogleSpeedView() {
            if (isCurrentSpeedViewGoogle()) {
                return
            }
            clearSpeedViewContainer()
            val speedView = SpeedGoogleView(this@MainActivity)
            speedView.setOnMoreClickedListener(onMoreClickedListener)
            speedViewContainer.addView(speedView)
        }
    }

    private fun createUserAction(): MainActivityContract.UserAction {
        val screen = createScreen()
        val speedManager = ApplicationGraph.getSpeedManager()
        val permissionManager = ApplicationGraph.getPermissionManager()
        val themeManager = ApplicationGraph.getThemeManager()
        return MainActivityPresenter(
            screen,
            speedManager,
            permissionManager,
            themeManager
        )
    }
}
