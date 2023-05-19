package com.github.akagawatsurunaki.android.novapproandroid.util

import android.widget.Toast
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage

object ServiceResultUtil {
    fun isSuccess(content: android.content.Context, serviceMessage: ServiceMessage) : Boolean {
        if (serviceMessage.level != ServiceMessage.Level.SUCCESS) {
            show(content, serviceMessage)
            return false
        }
        return true
    }

    fun isFailed(content: android.content.Context, serviceMessage: ServiceMessage) : Boolean {
        return !isSuccess(content, serviceMessage)
    }

    fun show(content: android.content.Context, serviceMessage: ServiceMessage): Boolean {
        Toast.makeText(content, serviceMessage.message.toString(), Toast.LENGTH_LONG).show()
        return serviceMessage.level != ServiceMessage.Level.SUCCESS
    }
}