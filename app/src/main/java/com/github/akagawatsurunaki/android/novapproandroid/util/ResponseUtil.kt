package com.github.akagawatsurunaki.android.novapproandroid.util

import android.util.Log
import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.TypeReference
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Response
import org.apache.commons.lang3.tuple.ImmutablePair
import java.io.File

object ResponseUtil {

    private val defaultServiceMessage = ServiceMessage(Level.FATAL, "诶我！出戳啦！")

    val defaultResult = Pair(
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

    inline fun <reified Model> getServiceResult(
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

    inline fun <reified Model> getServiceResult(
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

    inline fun <reified Model> getServiceResult(servletValue: String): Pair<ServiceMessage, Model?> {
        var result: Pair<ServiceMessage, Model?>
        runBlocking {
            withContext(Dispatchers.IO) {
                val response = ConnUtil.sendGetRequest(servletValue)
                result = parseResponse<Model>(response)
            }
        }
        return result
    }

    private fun parseServiceMessage(response: Response?): ServiceMessage {
        return try {
            val jsonString = response?.body?.string()
            Log.i("json", "parseServiceMessage: $jsonString")
            var result: ServiceMessage = defaultServiceMessage
            if (jsonString != null) {
                val pairType = object : TypeReference<ServiceMessage>() {}
                result = JSONObject.parseObject(jsonString, pairType)
            } else {
                Log.e("响应错误", "响应体为NULL")
            }
            result
        } catch (e: Exception) {
            e.printStackTrace()
            ServiceMessage(Level.FATAL, "JSON字符串解析失败")
        }
    }

    inline fun <reified Model> parseResponse(response: Response?): Pair<ServiceMessage, Model?> {
        var result: Pair<ServiceMessage, Model?> = defaultResult
        try {
            val jsonString = response?.body?.string()
            Log.i("json", "parseServiceMessage: $jsonString")
            if (jsonString != null) {
                val pairType =
                    object : TypeReference<ImmutablePair<ServiceMessage, Model>>() {}.type
                val pair =
                    JSON.parseObject<ImmutablePair<ServiceMessage, Model>>(jsonString, pairType)
                result = Pair(pair.left, pair.right)
            } else {
                Log.e("响应错误", "响应体为NULL")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}
