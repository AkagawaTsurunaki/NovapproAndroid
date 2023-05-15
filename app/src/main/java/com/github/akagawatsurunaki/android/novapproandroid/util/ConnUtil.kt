package com.github.akagawatsurunaki.android.novapproandroid.util

import com.github.akagawatsurunaki.android.novapproandroid.config.Config
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object ConnUtil {

    /**
     * 传入Servlet的访问路径，以及post请求的参数map
     */
    fun sendPostRequest(servletValue: String, kvMap: Map<String, String>): Response? {
        var response: Response? = null
        try {
            Thread {
                val url = Config.prefix + servletValue

                val requestBody = FormBody.Builder().apply {
                    kvMap.forEach { entry -> add(entry.key, entry.value) }
                }.build()

                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                val client = OkHttpClient()
                response = client.newCall(request).execute()
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

}