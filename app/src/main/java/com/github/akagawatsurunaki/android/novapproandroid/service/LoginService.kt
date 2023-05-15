package com.github.akagawatsurunaki.android.novapproandroid.service

import android.content.Intent
import android.util.Log
import androidx.annotation.Nullable
import androidx.compose.runtime.Immutable
import com.alibaba.fastjson2.JSON
import com.github.akagawatsurunaki.android.novapproandroid.config.Config
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

object LoginService {
    fun login(userId: String, rawPassword: String): Pair<ServiceMessage, User?>? {
        var result : Pair<ServiceMessage, User?>? = null

        Thread {
            val url = Config.prefix + "/android/login"

            val requestBody = FormBody.Builder()
                .add("userId", userId)
                .add("rawPassword", rawPassword)
                .build()
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val client = OkHttpClient()
            val response = client.newCall(request).execute()

            val responseData = response.body?.string()

            // TODO(这里会有错误发生，因为类型不匹配)


        }
        return null
    }

    fun c() {
        Thread {
            val url = Config.prefix + "/android/login"
            val client = OkHttpClient()
            val request = Request.Builder().url(url)
                .build()
            val response = client.newCall(request = request).execute()
            val responseStr = response.body?.string()
            Log.d("response", responseStr!!)
        }.start()
    }
}