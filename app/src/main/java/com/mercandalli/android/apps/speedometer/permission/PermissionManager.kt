package com.mercandalli.android.apps.speedometer.permission

interface PermissionManager {

    fun hasGpsPermission(): Boolean

    fun requestGpsPermission()

    fun notifyPermissionChanged()

    fun registerPermissionListener(listener: PermissionListener)

    fun unregisterPermissionListener(listener: PermissionListener)

    interface PermissionListener {

        fun onPermissionChanged()
    }
}
