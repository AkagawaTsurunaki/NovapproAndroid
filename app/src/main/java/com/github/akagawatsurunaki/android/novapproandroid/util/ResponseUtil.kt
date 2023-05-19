package com.github.akagawatsurunaki.android.novapproandroid.util

import android.util.Log
import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.TypeReference
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Response
import org.apache.commons.lang3.tuple.ImmutablePair
import java.io.File

object ResponseUtil {

    private val defaultServiceMessage = ServiceMessage(ServiceMessage.Level.FATAL, "诶我！出戳啦！")
    private val defaultResult = Pair(
        defaultServiceMessage,
        null
    )

    fun getServiceMessage(
        servletValue: String,
        params: Map<String, String>,
        fileParams: Map<String, File>
    ): ServiceMessage {
        var result: ServiceMessage = defaultServiceMessage
        runBlocking {
            withContext(Dispatchers.IO) {
                val response = ConnUtil.sendPostRequest(servletValue, params, fileParams)
                result = parseServiceMessage(response)
            }
        }
        return result
    }

    fun getServiceMessage(
        servletValue: String,
        params: Map<String, String>
    ): ServiceMessage {
        var result: ServiceMessage
        runBlocking {
            withContext(Dispatchers.IO) {
                val response = ConnUtil.sendPostRequest(servletValue, params)
                result = parseServiceMessage(response)
            }
        }
        return result
    }

    fun <Model> getServiceResult(
        servletValue: String,
        params: Map<String, String>,
        fileParams: Map<String, File>
    ): Pair<ServiceMessage, Model?> {
        var result: Pair<ServiceMessage, Model?>
        runBlocking {
            withContext(Dispatchers.IO) {
                val response = ConnUtil.sendPostRequest(servletValue, params, fileParams)
                result = parseResponse<Model>(response)
            }
        }
        return result
    }

    fun <Model> getServiceResult(
        servletValue: String,
        params: Map<String, String>
    ): Pair<ServiceMessage, Model?> {
        var result: Pair<ServiceMessage, Model?>
        runBlocking {
            withContext(Dispatchers.IO) {
                val response = ConnUtil.sendPostRequest(servletValue, params)
                result = parseResponse<Model>(response)
            }
        }
        return result
    }

    fun <Model> getServiceResult(servletValue: String): Pair<ServiceMessage, Model?> {
        var result: Pair<ServiceMessage, Model?>
        runBlocking {
            withContext(Dispatchers.IO) {
                val response = ConnUtil.sendGetRequest(servletValue)
                result = parseResponse<Model>(response)
            }}
        return result
    }

    private fun parseServiceMessage(response: Response?): ServiceMessage {
        val jsonString = response?.body?.string()
        var result: ServiceMessage = defaultServiceMessage
        if (jsonString != null) {
            val pairType = object : TypeReference<ServiceMessage>() {}
            result = JSONObject.parseObject(jsonString, pairType)
        } else {
            Log.e("响应错误", "响应体为NULL")
        }
        return result
    }

    private fun <Model> parseResponse(response: Response?): Pair<ServiceMessage, Model?> {
        val jsonString = response?.body?.string()
        var result: Pair<ServiceMessage, Model?> = defaultResult
        if (jsonString != null) {
            val pairType = object : TypeReference<ImmutablePair<ServiceMessage, Model>>() {}
            val pair = JSON.parseObject(jsonString, pairType)
            // TODO(java.lang.ClassCastException: com.alibaba.fastjson2.JSONObject cannot be cast to com.github.akagawatsurunaki.android.novapproandroid.model.User)
            result = Pair(pair.left, pair.right)
        } else {
            Log.e("响应错误", "响应体为NULL")
        }
        return result
    }
}
