package com.mercandalli.android.apps.speedometer.main

import android.annotation.SuppressLint
import android.content.Context
import com.mercandalli.android.apps.speedometer.main_thread.MainThreadModule
import com.mercandalli.android.apps.speedometer.network.NetworkModule
import com.mercandalli.android.apps.speedometer.remote_config.RemoteConfigModule
import com.mercandalli.android.apps.speedometer.screen.ScreenModule
import com.mercandalli.android.apps.speedometer.theme.ThemeModule
import com.mercandalli.android.apps.speedometer.toast.ToastModule
import com.mercandalli.android.apps.speedometer.update.UpdateModule
import com.mercandalli.android.apps.speedometer.version.VersionModule

class ApplicationGraph(
    private val context: Context
) {

    private val networkModule = NetworkModule()
    private val okHttpClientLazy = networkModule.createOkHttpClientLazy()

    private val mainThreadPostInternal by lazy { MainThreadModule().createMainThreadPost() }
    private val networkInternal by lazy { networkModule.createNetwork() }
    private val remoteConfigInternal by lazy { RemoteConfigModule().createRemoteConfig() }
    private val screenManagerInternal by lazy { ScreenModule(context).createScreenManager() }
    private val toastManagerInternal by lazy { ToastModule(context).createToastManager() }
    private val themeManagerInternal by lazy { ThemeModule(context).createThemeManager() }
    private val updateManagerInternal by lazy { UpdateModule(context).createUpdateManager() }
    private val versionManagerInternal by lazy { VersionModule().createVersionManager(context) }

    companion object {

        @JvmStatic
        @SuppressLint("StaticFieldLeak")
        private var graph: ApplicationGraph? = null

        @JvmStatic
        fun init(context: Context) {
            if (graph == null) {
                graph = ApplicationGraph(context.applicationContext)
            }
        }

        fun getMainThreadPost() = graph!!.mainThreadPostInternal
        fun getNetwork() = graph!!.networkInternal
        fun getOkHttpClientLazy() = graph!!.okHttpClientLazy
        fun getRemoteConfig() = graph!!.remoteConfigInternal
        fun getScreenManager() = graph!!.screenManagerInternal
        fun getToastManager() = graph!!.toastManagerInternal
        fun getThemeManager() = graph!!.themeManagerInternal
        fun getUpdateManager() = graph!!.updateManagerInternal
        fun getVersionManager() = graph!!.versionManagerInternal
    }
}
