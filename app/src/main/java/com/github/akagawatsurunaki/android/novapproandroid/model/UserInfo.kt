package com.github.akagawatsurunaki.android.novapproandroid.model

import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType

data class UserInfo(
    var userId: Int,
    var username: String,
    var userType: UserType,
    var isAutoLogin: Boolean,
) {

}
