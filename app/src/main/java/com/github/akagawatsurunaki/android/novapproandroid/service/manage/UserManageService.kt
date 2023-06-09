package com.github.akagawatsurunaki.android.novapproandroid.service.manage

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.util.ResponseUtil

object UserManageService {

    fun getAllUsers(): Pair<ServiceMessage, List<User>?> {
        return ResponseUtil.getServiceResult<List<User>>(
            servletValue = "/android/userManageService/getAllUsers"
        )
    }

    fun updateAllUsers(userIds: List<String>): Pair<ServiceMessage, List<User>?> {
        // TODO(to test here, idk there is an error or not)
        return ResponseUtil.getServiceResult<List<User>>(
            servletValue = "/android/userManageService/updateAllUsers",
            // TODO(极容易引发错误)
            mapOf("userId" to JSON.toJSONString(userIds))
        )
    }

    fun updateUser(user: User): Pair<ServiceMessage, User?> {
        return ResponseUtil.getServiceResult<User>(
            servletValue = "/android/userManageService/updateUser",
            // TODO(极容易引发错误)
            mapOf(
                "id" to user.id.toString(),
                "username" to user.username.toString(),
                "type" to user.type.toString()
            )
        )
    }

    fun deleteUser(userId: String): Pair<ServiceMessage, User?> {
        // TODO(to test here, idk there is an error or not)
        return ResponseUtil.getServiceResult<User>(
            servletValue = "/android/userManageService/deleteUser",
            mapOf("userId" to userId)
        )
    }

    fun addUser(user: User): Pair<ServiceMessage, User?> {
        return ResponseUtil.getServiceResult<User>(
            servletValue = "/android/userManageService/addUser",
            // TODO(极容易引发错误)
            mapOf("user" to JSONObject.toJSONString(user))
        )
    }

    fun resetUserPassword(userId: String): Pair<ServiceMessage, User?> {
        return ResponseUtil.getServiceResult<User>(
            servletValue = "/android/userManageService/resetUserPassword",
            mapOf("userId" to userId)
        )
    }

}