@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.apps.speedometer.remote_config

import com.mercandalli.android.apps.speedometer.main_thread.MainThreadPost
import com.mercandalli.android.apps.speedometer.update.UpdateManager

import java.util.ArrayList
import java.util.HashMap

/**
 * This class is used to get the remote configuration
 */
internal class RemoteConfigImpl(
    updateManager: UpdateManager,
    private val mainThreadPost: MainThreadPost
) : RemoteConfig {

    private val listeners = ArrayList<RemoteConfig.RemoteConfigListener>()

    private var isInitializedInternal = false
    private val remoteConfigs = HashMap<String, String>()

    init {
        remoteConfigs[KEY_SUBSCRIPTION_FULL_VERSION_SKU] =
            "googleplay.com.mercandalli.android.apps.files.subscription.1"
        // TODO - Network call
        // TODO - Storage
        notifyInitialized()
    }

    override fun isInitialized() = isInitializedInternal

    override fun getSubscriptionFullVersionSku(): String? {
        return remoteConfigs[KEY_SUBSCRIPTION_FULL_VERSION_SKU]
    }

    override fun registerListener(listener: RemoteConfig.RemoteConfigListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    override fun unregisterListener(listener: RemoteConfig.RemoteConfigListener) {
        listeners.remove(listener)
    }

    private fun notifyInitialized() {
        if (!mainThreadPost.isOnMainThread()) {
            mainThreadPost.post(Runnable { this.notifyInitialized() })
            return
        }
        for (listener in listeners) {
            listener.onInitialized()
        }
    }

    companion object {
        private const val KEY_SUBSCRIPTION_FULL_VERSION_SKU = "subscription_full_version_sku"
    }
}
