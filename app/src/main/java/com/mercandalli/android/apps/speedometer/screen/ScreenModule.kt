@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.apps.speedometer.screen

import android.content.Context

class ScreenModule(
    private val context: Context
) {

    fun createScreenManager(): ScreenManager {
        return ScreenManagerImpl(
            context
        )
    }
}
