package com.mercandalli.android.apps.speedometer.toast

import android.content.Context
import com.mercandalli.android.apps.speedometer.main.ApplicationGraph

class ToastModule(
    private val context: Context
) {

    fun createToastManager(): ToastManager {
        val mainThreadPost = ApplicationGraph.getMainThreadPost()
        return ToastManagerImpl(
            context.applicationContext,
            mainThreadPost
        )
    }
}
