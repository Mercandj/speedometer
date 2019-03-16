package com.mercandalli.android.apps.speedometer.update

import android.content.Context
import com.mercandalli.android.apps.speedometer.main.ApplicationGraph

class UpdateModule(
    private val context: Context
) {

    fun createUpdateManager(): UpdateManager {
        val versionManager = ApplicationGraph.getVersionManager()
        val sharedPreferences = context.getSharedPreferences(
            UpdateManagerImpl.PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        return UpdateManagerImpl(
            sharedPreferences,
            versionManager
        )
    }
}
