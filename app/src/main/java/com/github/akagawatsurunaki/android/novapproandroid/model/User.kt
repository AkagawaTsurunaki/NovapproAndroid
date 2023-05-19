package com.github.akagawatsurunaki.android.novapproandroid.model

import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType

data class User(
    var id: Int?,
    var username: String?,
    var rawPassword: String?,
    var type: UserType?
)
