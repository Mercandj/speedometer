package com.mercandalli.android.apps.speedometer.main

import android.app.Application

/**
 * The [Application] of this project.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ApplicationGraph.init(this)

    }
}
