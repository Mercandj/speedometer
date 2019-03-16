package com.mercandalli.android.apps.speedometer.version

interface VersionManager {

    fun getVersionName(): String

    fun getFirstInstallTime(): Long
}
