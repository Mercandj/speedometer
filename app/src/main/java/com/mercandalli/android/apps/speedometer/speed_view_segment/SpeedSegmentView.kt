package com.mercandalli.android.apps.speedometer.speed_view_segment

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
    private val speedUnit: TextView = view.findViewById(R.id.view_speed_segment_speed_unit)
    private val startStopButton: TextView = view.findViewById(R.id.view_speed_segment_start)
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
            textFirstDigits: String,
            textLastDigits: String
        ) {
            val text = "$textFirstDigits$textLastDigits"
            val spannableString = SpannableString(
                text
            )
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#F0F0F0")),
                0,
                textFirstDigits.count(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            speed.text = spannableString
        }

        override fun setSpeedUnitText(text: String) {
            speedUnit.text = text
        }

        override fun setStartStopButtonText(text: String) {
            startStopButton.text = text
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
        val speedManager = ApplicationGraph.getSpeedManager()
        val permissionManager = ApplicationGraph.getPermissionManager()
        return SpeedSegmentViewPresenter(
            screen,
            speedManager,
            permissionManager
        )
    }
}
