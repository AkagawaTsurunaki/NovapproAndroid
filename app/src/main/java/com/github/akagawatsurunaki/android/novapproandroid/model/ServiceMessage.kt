package com.github.akagawatsurunaki.android.novapproandroid.model

class ServiceMessage(var level: Level?, var message: String?) {
    enum class Level {
        SUCCESS, INFO, WARN, ERROR, FATAL
    }

}
