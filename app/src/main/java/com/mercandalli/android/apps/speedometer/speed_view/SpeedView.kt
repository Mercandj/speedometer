package com.mercandalli.android.apps.speedometer.speed_view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

open class SpeedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var onMoreClickedListener: OnMoreClickedListener? = null

    fun setOnMoreClickedListener(listener: OnMoreClickedListener?) {
        onMoreClickedListener = listener
    }

    protected fun notifyOnMoreClicked() {
        onMoreClickedListener?.onMoreClicked()
    }

    interface OnMoreClickedListener {

        fun onMoreClicked()
    }
}
