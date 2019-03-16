package com.mercandalli.android.apps.speedometer.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build

class PermissionManagerImpl(
    private val permissionRequestAddOn: PermissionRequestAddOn
) : PermissionManager {

    private val listeners = ArrayList<PermissionManager.PermissionListener>()

    override fun requestGpsPermission() {
        if (hasGpsPermission()) return
        permissionRequestAddOn.requestStoragePermission()
    }

    override fun hasGpsPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val checkSelfFineLocationPermission = permissionRequestAddOn.checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (checkSelfFineLocationPermission != PackageManager.PERMISSION_GRANTED) {
                return false
            }
            val checkSelfCoarseLocationPermission = permissionRequestAddOn.checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            if (checkSelfCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun notifyPermissionChanged() {
        for (listener in listeners) {
            listener.onPermissionChanged()
        }
    }

    override fun registerPermissionListener(listener: PermissionManager.PermissionListener) {
        if (listeners.contains(listener)) {
            return
        }
        listeners.add(listener)
    }

    override fun unregisterPermissionListener(listener: PermissionManager.PermissionListener) {
        listeners.remove(listener)
    }

    interface PermissionRequestAddOn {

        fun requestStoragePermission()

        fun checkSelfPermission(permission: String): Int
    }
}
