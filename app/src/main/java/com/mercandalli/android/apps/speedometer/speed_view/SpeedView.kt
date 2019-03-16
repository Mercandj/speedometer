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

    fun setOnMoreClickedListener(listener: OnMoreClickedListener?) {
        onMoreClickedListener = listener
    }

    protected fun notifyOnMoreClicked(view: View) {
        onMoreClickedListener?.onMoreClicked(view)
    }

    interface OnMoreClickedListener {

        fun onMoreClicked(view: View)
    }
}
