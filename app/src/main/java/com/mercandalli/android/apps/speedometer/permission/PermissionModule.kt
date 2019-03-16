package com.mercandalli.android.apps.speedometer.permission

import android.content.Context
import androidx.core.content.ContextCompat

class PermissionModule(
    private val context: Context
) {

    fun createPermissionManager(): PermissionManager {
        val addOn = object : PermissionManagerImpl.PermissionRequestAddOn {
            override fun requestStoragePermission() {
                PermissionActivity.start(context)
            }

            override fun checkSelfPermission(permission: String): Int {
                return ContextCompat.checkSelfPermission(context, permission)
            }
        }
        return PermissionManagerImpl(
            addOn
        )
    }
}
