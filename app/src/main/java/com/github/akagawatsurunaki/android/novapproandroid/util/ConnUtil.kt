package com.github.akagawatsurunaki.android.novapproandroid.util

import com.github.akagawatsurunaki.android.novapproandroid.config.Config
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import java.io.File
import java.io.IOException

object ConnUtil {

    /**
     * 传入Servlet的访问路径，以及post请求的参数map，注意必须在Thread中调用此方法
     */
    fun sendPostRequest(servletValue: String, kvMap: Map<String, String>): Response? {
        var response: Response? = null
        try {

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


        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    /**
     * 传入Servlet的访问路径，以及post请求的参数map，注意必须在Thread中调用此方法
     */
    fun sendPostRequest(
        servletValue: String,
        kvMap: Map<String, String>,
        fileMap: Map<String, File>
    ): Response? {
        var response: Response? = null
        try {

            val url = Config.prefix + servletValue

            // 构建MultipartBody对象
            val requestBody = MultipartBody.Builder()
                .apply {
                    // 添加普通参数
                    kvMap.forEach { entry -> addFormDataPart(entry.key, entry.value) }

                    // 添加文件参数
                    fileMap.forEach { entry ->
                        val file = entry.value
                        val requestBody =
                            file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
                        addFormDataPart(entry.key, file.name, requestBody)
                    }
                }
                .setType(MultipartBody.FORM)
                .build()

            // 创建Request对象
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            // 创建OkHttpClient实例对象并发送请求
            val client = OkHttpClient()
            response = client.newCall(request).execute()

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }


    fun sendGetRequest(servletValue: String): Response? {
        var response: Response? = null
        try {

            val url = Config.prefix + servletValue

            val request = Request.Builder()
                .url(url)
                .build()

            val client = OkHttpClient()
            response = client.newCall(request).execute()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

}