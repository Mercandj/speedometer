package com.mercandalli.android.apps.speedometer.permission

class PermissionPresenter(
    private val screen: PermissionContract.Screen,
    private val permissionManager: PermissionManager
) : PermissionContract.UserAction {

    override fun onPermissionAllowClicked() {
        screen.requestStoragePermission()
    }

    override fun onUserAcceptPermission() {
        permissionManager.notifyPermissionChanged()
    }
}
