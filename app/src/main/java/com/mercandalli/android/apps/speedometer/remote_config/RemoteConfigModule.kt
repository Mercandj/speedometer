@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.apps.speedometer.remote_config

import com.mercandalli.android.apps.speedometer.main.ApplicationGraph
import com.mercandalli.android.apps.speedometer.main_thread.MainThreadPost
import com.mercandalli.android.apps.speedometer.update.UpdateManager

/**
 * A module for the remote config .
 */
class RemoteConfigModule {

    fun createRemoteConfig(): RemoteConfig {
        val mainThreadPost = ApplicationGraph.getMainThreadPost()
        val updateManager = ApplicationGraph.getUpdateManager()
        return provideRemoteConfigStatic(
            updateManager,
            mainThreadPost
        )
    }

    companion object {

        private var instance: RemoteConfig? = null

        @JvmStatic
        fun provideRemoteConfigStatic(
            updateManager: UpdateManager,
            mainThreadPost: MainThreadPost
        ): RemoteConfig {
            if (instance == null) {
                instance = RemoteConfigImpl(
                    updateManager,
                    mainThreadPost
                )
            }
            return instance!!
        }
    }
}
