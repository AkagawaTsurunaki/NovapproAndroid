package com.github.akagawatsurunaki.android.novapproandroid.model
enum class Level {
    SUCCESS, INFO, WARN, ERROR, FATAL
}
data class ServiceMessage(var messageLevel: Level?, var message: String?) {}
