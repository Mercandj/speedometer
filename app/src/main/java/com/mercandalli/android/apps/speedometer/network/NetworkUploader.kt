package com.mercandalli.android.apps.speedometer.network

import org.json.JSONObject

interface NetworkUploader {

    fun postUploadSync(
        url: String,
        headers: Map<String, String>,
        jsonObject: JSONObject,
        javaFile: java.io.File,
        listener: NetworkManager.UploadProgressListener
    ): String?
}
