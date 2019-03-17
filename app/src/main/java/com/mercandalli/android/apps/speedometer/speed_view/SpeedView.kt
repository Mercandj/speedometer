package com.mercandalli.android.apps.speedometer.speed_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

open class SpeedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var onMoreClickedListener: OnMoreClickedListener? = null
    private var onSpeedUnitClickedListener: OnSpeedUnitClickedListener? = null

    fun setOnMoreClickedListener(listener: OnMoreClickedListener?) {
        onMoreClickedListener = listener
    }

    fun setOnSpeedUnitClickedListener(listener: OnSpeedUnitClickedListener?) {
        onSpeedUnitClickedListener = listener
    }

    protected fun notifyOnMoreClicked(view: View) {
        onMoreClickedListener?.onMoreClicked(view)
    }

    protected fun notifyOnSpeedUnitClicked(view: View) {
        onSpeedUnitClickedListener?.onSpeedUnitClicked(view)
    }

    interface OnMoreClickedListener {

        fun onMoreClicked(view: View)
    }

    interface OnSpeedUnitClickedListener {

        fun onSpeedUnitClicked(view: View)
    }
}
