package com.github.akagawatsurunaki.android.novapproandroid.model

class ServiceMessage(val level: Level, val message: String) {
    enum class Level {
        SUCCESS, INFO, WARN, ERROR, FATAL
    }

    companion object {
        fun of(level: Level, message: String): ServiceMessage {
            return ServiceMessage(level, message)
        }
    }


}
