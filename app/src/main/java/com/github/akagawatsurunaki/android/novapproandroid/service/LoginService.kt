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
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.util.ConnUtil
import com.github.akagawatsurunaki.android.novapproandroid.util.ResponseUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.lang3.tuple.ImmutablePair

object LoginService {
    fun login(userId: String, rawPassword: String): Pair<ServiceMessage, User?> {
        return ResponseUtil.getServiceResult<User>(
            servletValue = "/android/login",
            mapOf("userId" to userId, "rawPassword" to rawPassword)
        )
    }

}