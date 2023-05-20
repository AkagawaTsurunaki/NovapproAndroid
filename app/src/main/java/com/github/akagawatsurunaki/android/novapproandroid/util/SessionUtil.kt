package com.github.akagawatsurunaki.android.novapproandroid.util

import okhttp3.Response

object SessionUtil {
    fun getSession(res: Response?) = res?.run {
        val session = res.headers.values("Set-Cookie")[0]
        session.substring(0, session.indexOf(";"))
    }

}