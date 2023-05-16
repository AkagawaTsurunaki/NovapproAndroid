package com.github.akagawatsurunaki.android.novapproandroid.util

import android.util.Log
import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.TypeReference
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage

object ResponseUtil {

    fun <Model> getServiceResult(
        servletValue: String,
        params: Map<String, String>
    ): Pair<ServiceMessage, Model?> {
        var result: Pair<ServiceMessage, Model?>? = null
        Thread {
            val response = ConnUtil.sendPostRequest(servletValue, params)

            val jsonString = response?.body?.string()

            if (jsonString != null) {
                val pairType = object : TypeReference<Pair<ServiceMessage, Model>>() {}
                result = JSONObject.parseObject(jsonString, pairType)
            } else {
                Log.e("响应错误", "响应体为NULL")
            }

        }.start()
        return result ?: Pair(
            ServiceMessage(ServiceMessage.Level.FATAL, "未知错误导致的服务失败"),
            null
        )
    }
    fun <Model> getServiceResult(servletValue: String): Pair<ServiceMessage, Model?> {
        var result: Pair<ServiceMessage, Model?>? = null
        Thread {
            val response = ConnUtil.sendGetRequest(servletValue)

            val jsonString = response?.body?.string()

            if (jsonString != null) {
                val pairType = object : TypeReference<Pair<ServiceMessage, Model>>() {}
                result = JSONObject.parseObject(jsonString, pairType)
            } else {
                Log.e("响应错误", "响应体为NULL")
            }

        }.start()
        return result ?: Pair(
            ServiceMessage(
                ServiceMessage.Level.FATAL,
                "响应体为NULL。请检查URL是否正确，或者服务器是否已经开启，或者类型不匹配等。"
            ),
            null
        )
    }
}
