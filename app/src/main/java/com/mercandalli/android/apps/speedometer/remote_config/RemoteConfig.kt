@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.apps.speedometer.remote_config

interface RemoteConfig {

    /**
     * @return true if the [RemoteConfig] is initialized, false otherwise
     */
    fun isInitialized(): Boolean

    fun getSubscriptionFullVersionSku(): String?

    fun registerListener(listener: RemoteConfigListener)

    fun unregisterListener(listener: RemoteConfigListener)

    interface RemoteConfigListener {

        /***
         * is triggered when the [RemoteConfig] is initialized
         */
        fun onInitialized()
    }
}
