@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.apps.speedometer.screen

interface ScreenManager {

    fun startHomeActivity(forceNoOnBoarding: Boolean = false)

    fun startOnBoarding()

    fun stopOnBoarding()

    fun startOnBoardingLoading()
}
