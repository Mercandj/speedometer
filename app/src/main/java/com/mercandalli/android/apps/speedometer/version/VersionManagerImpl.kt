package com.mercandalli.android.apps.speedometer.version

import android.content.pm.PackageInfo

class VersionManagerImpl(
    private val delegate: Delegate
) : VersionManager {

    private val packageInfo by lazy {
        delegate.getAppPackageInfo()
    }

    override fun getVersionName() = packageInfo.versionName!!

    override fun getFirstInstallTime() = packageInfo.firstInstallTime

    interface Delegate {

        fun getAppPackageInfo(): PackageInfo
    }
}
