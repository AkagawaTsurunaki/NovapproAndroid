package com.github.akagawatsurunaki.android.novapproandroid.model

import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType

data class User(
    val id: Int,
    val username: String,
    val rawPassword: String,
    val type: UserType
)
