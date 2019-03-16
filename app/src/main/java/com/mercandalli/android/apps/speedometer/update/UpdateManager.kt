package com.mercandalli.android.apps.speedometer.update

interface UpdateManager {

    fun isFirstRunAfterUpdate(): Boolean

    fun isFirstRun(): Boolean
}
