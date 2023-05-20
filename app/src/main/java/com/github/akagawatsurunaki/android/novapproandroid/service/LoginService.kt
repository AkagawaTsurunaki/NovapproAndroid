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
import com.github.akagawatsurunaki.android.novapproandroid.model.Course
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.util.ConnUtil
import com.github.akagawatsurunaki.android.novapproandroid.util.ResponseUtil
import com.github.akagawatsurunaki.android.novapproandroid.util.SessionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.apache.commons.lang3.tuple.ImmutablePair

object LoginService {

    var session = ""

    fun login(userId: String, rawPassword: String): Pair<ServiceMessage, User?> {
        var result: Pair<ServiceMessage, User?> = Pair(ServiceMessage(Level.ERROR, "登陆失败"), null)

        runBlocking {
            withContext(Dispatchers.IO) {
                var response: Response? = null
                try {

                    val url = Config.prefix + "/android/login"

                    val requestBody = FormBody.Builder().apply {
                        add("userId", userId)
                        add("rawPassword", rawPassword)
                    }.build()

                    val request = Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build()

                    val client = OkHttpClient()
                    response = client.newCall(request).execute()

                    session = SessionUtil.getSession(response)!!
                    ConnUtil.session = session

                    Log.i("login", "login: $session")

                    val jsonString = response.body?.string()
                    result = ResponseUtil.defaultResult
                    if (jsonString != null) {
                        val pairType = object : TypeReference<ImmutablePair<ServiceMessage, User>>() {}.type
                        val pair = JSON.parseObject<ImmutablePair<ServiceMessage, User>>(jsonString, pairType)
                        result = Pair(pair.left, pair.right)
                        // 初始化session
                        ConnUtil.session = SessionUtil.getSession(response) ?: ""
                    } else {
                        Log.e("响应错误", "响应体为NULL")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return result

    }

}