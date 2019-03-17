package com.mercandalli.android.apps.speedometer.main

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.mercandalli.android.apps.speedometer.R
import com.mercandalli.android.apps.speedometer.activity.ActivityExtension.bind
import com.mercandalli.android.apps.speedometer.speed_view.SpeedView
import com.mercandalli.android.apps.speedometer.speed_view_google.SpeedGoogleView
import com.mercandalli.android.apps.speedometer.speed_view_segment.SpeedSegmentView
import com.mercandalli.android.apps.speedometer.speed_view_tesla.SpeedTeslaView

class MainActivity : AppCompatActivity() {

    private val speedViewContainer: FrameLayout by bind(R.id.activity_main_speed_view_container)
    private val onMoreClickedListener = createOnMoreClickedListener()
    private val onSpeedUnitClickedListener = createOnSpeedUnitClickedListener()
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
        currentSpeedView.setOnSpeedUnitClickedListener(null)
        speedViewContainer.removeAllViews()
    }

    private fun setSpeedView(speedView: SpeedView) {
        clearSpeedViewContainer()
        speedView.setOnMoreClickedListener(onMoreClickedListener)
        speedView.setOnSpeedUnitClickedListener(onSpeedUnitClickedListener)
        speedViewContainer.addView(speedView)
    }

    private fun createOnMoreClickedListener() = object : SpeedView.OnMoreClickedListener {
        override fun onMoreClicked(view: View) {
            val popupMenu = PopupMenu(this@MainActivity, view, Gravity.END)
            popupMenu.menuInflater.inflate(R.menu.menu_more, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_more_view -> userAction.onMoreViewClicked()
                    R.id.menu_more_theme -> userAction.onMoreThemeClicked()
                }
                false
            }
            popupMenu.show()
        }
    }

    private fun createOnSpeedUnitClickedListener() = object : SpeedView.OnSpeedUnitClickedListener {
        override fun onSpeedUnitClicked(view: View) {
            userAction.onSpeedUnitClicked()
        }
    }

    private fun createScreen() = object : MainActivityContract.Screen {
        override fun showTeslaSpeedView() {
            if (isCurrentSpeedViewTesla()) {
                return
            }
            setSpeedView(SpeedTeslaView(this@MainActivity))
        }

        override fun showSegmentSpeedView() {
            if (isCurrentSpeedViewSegment()) {
                return
            }
            setSpeedView(SpeedSegmentView(this@MainActivity))
        }

        override fun showGoogleSpeedView() {
            if (isCurrentSpeedViewGoogle()) {
                return
            }
            setSpeedView(SpeedGoogleView(this@MainActivity))
        }

        override fun setWindowBackgroundColorRes(
            @ColorRes colorRes: Int
        ) {
            val color = ContextCompat.getColor(this@MainActivity, colorRes)
            window.setBackgroundDrawable(ColorDrawable(color))
        }
    }

    private fun createUserAction(): MainActivityContract.UserAction {
        val screen = createScreen()
        val permissionManager = ApplicationGraph.getPermissionManager()
        val speedManager = ApplicationGraph.getSpeedManager()
        val speedUnitManager = ApplicationGraph.getSpeedUnitManager()
        val themeManager = ApplicationGraph.getThemeManager()
        return MainActivityPresenter(
            screen,
            permissionManager,
            speedManager,
            speedUnitManager,
            themeManager
        )
    }
}
