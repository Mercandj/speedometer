package com.mercandalli.android.apps.speedometer.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mercandalli.android.apps.speedometer.R
import com.mercandalli.android.apps.speedometer.activity.ActivityExtension.bind

class MainActivity : AppCompatActivity() {

    private val speed: TextView by bind(R.id.activity_main_speed)
    private val startStopButton: TextView by bind(R.id.activity_main_start)
    private val userAction = createUserAction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startStopButton.setOnClickListener {
            userAction.onStartClicked(
                startStopButton.text.toString()
            )
        }
        userAction.onCreate()
    }

    override fun onDestroy() {
        userAction.onDestroy()
        super.onDestroy()
    }

    private fun createScreen() = object : MainActivityContract.Screen {

        override fun setSpeed(text: String) {
            speed.text = text
        }

        override fun setStartStopButtonText(text: String) {
            startStopButton.text = text
        }
    }

    private fun createUserAction(): MainActivityContract.UserAction {
        val screen = createScreen()
        val speedManager = ApplicationGraph.getSpeedManager()
        val permissionManager = ApplicationGraph.getPermissionManager()
        return MainActivityPresenter(
            screen,
            speedManager,
            permissionManager
        )
    }
}
