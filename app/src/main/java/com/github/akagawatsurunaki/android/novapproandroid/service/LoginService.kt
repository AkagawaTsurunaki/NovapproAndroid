package com.github.akagawatsurunaki.android.novapproandroid.service

import android.app.Service
import android.content.Intent
import android.util.Log
import androidx.annotation.Nullable
import androidx.compose.runtime.Immutable
import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.TypeReference
import com.github.akagawatsurunaki.android.novapproandroid.config.Config
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.util.ConnUtil
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

object LoginService {
    fun login(userId: String, rawPassword: String): Pair<ServiceMessage, User?> {
        var result: Pair<ServiceMessage, User?>? = null
        Thread {
            val response = ConnUtil.sendPostRequest(
                servletValue = "/android/login",
                mapOf("userId" to userId, "rawPassword" to rawPassword)
            )
            val jsonString = response?.body?.string()

            if (jsonString != null) {
                val pairType = object : TypeReference<Pair<ServiceMessage, User?>>() {}
                result = JSONObject.parseObject(jsonString, pairType)
            } else {
                Log.e("Response Error", "Response body is empty!")
            }

        }.start()
        return result?: Pair(
            ServiceMessage(ServiceMessage.Level.FATAL, "未知错误导致的服务失败"),
            null)
    }

}