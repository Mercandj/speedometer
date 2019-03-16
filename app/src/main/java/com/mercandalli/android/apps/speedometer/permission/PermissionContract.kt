package com.mercandalli.android.apps.speedometer.permission

interface PermissionContract {

    interface UserAction {

        fun onPermissionAllowClicked()

        fun onUserAcceptPermission()
    }

    interface Screen {

        fun requestStoragePermission()
    }
}
