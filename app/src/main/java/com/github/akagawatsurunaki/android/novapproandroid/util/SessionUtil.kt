package com.github.akagawatsurunaki.android.novapproandroid.util

import okhttp3.Response

object SessionUtil {
    fun getSession(res: Response?): String? {
        val cookies = res?.headers?.values("Set-Cookie")
        if (!cookies.isNullOrEmpty()) {
            val session = cookies[0]
            return session.substring(0, session.indexOf(";"))
        }
        return null
    }

}