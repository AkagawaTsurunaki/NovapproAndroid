package com.github.akagawatsurunaki.android.novapproandroid.util

import android.util.Log
import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.TypeReference
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import okhttp3.Response
import java.io.File

object ResponseUtil {

    private val defaultServiceMessage = ServiceMessage(ServiceMessage.Level.FATAL, "默认服务响应，未知错误导致的服务失败")
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
        Thread {
            val response = ConnUtil.sendPostRequest(servletValue, params, fileParams)
            result = parseServiceMessage(response)
        }.start()
        return result
    }

    fun <Model> getServiceResult(
        servletValue: String,
        params: Map<String, String>,
        fileParams: Map<String, File>
    ): Pair<ServiceMessage, Model?> {
        var result: Pair<ServiceMessage, Model?> = defaultResult
        Thread {
            val response = ConnUtil.sendPostRequest(servletValue, params, fileParams)
            result = parseResponse<Model>(response)
        }.start()
        return result
    }

    fun <Model> getServiceResult(
        servletValue: String,
        params: Map<String, String>
    ): Pair<ServiceMessage, Model?> {
        var result: Pair<ServiceMessage, Model?> = defaultResult
        Thread {
            val response = ConnUtil.sendPostRequest(servletValue, params)
            result = parseResponse<Model>(response)
        }.start()
        return result
    }

    fun <Model> getServiceResult(servletValue: String): Pair<ServiceMessage, Model?> {
        var result: Pair<ServiceMessage, Model?> = defaultResult
        Thread {
            val response = ConnUtil.sendGetRequest(servletValue)
            result = parseResponse<Model>(response)
        }.start()
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
            val pairType = object : TypeReference<Pair<ServiceMessage, Model>>() {}
            result = JSONObject.parseObject(jsonString, pairType)
        } else {
            Log.e("响应错误", "响应体为NULL")
        }
        return result
    }
}
