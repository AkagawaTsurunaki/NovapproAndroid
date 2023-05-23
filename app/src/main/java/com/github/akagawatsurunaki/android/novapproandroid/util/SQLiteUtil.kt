package com.github.akagawatsurunaki.android.novapproandroid.util

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.github.akagawatsurunaki.android.novapproandroid.constant.Constant
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.helper.MyDbHelper
import com.github.akagawatsurunaki.android.novapproandroid.model.UserInfo
import java.lang.Exception

object SQLiteUtil {


    fun insert(context: Context, tableName: String = "user_info", userInfo: UserInfo) {
        try {
            val dbHelper = MyDbHelper(context, "novappro.db", Constant.DATABASE_VERSION)
            val db = dbHelper.writableDatabase
            val userInfoEntity = ContentValues().apply {
                // 开始组装第一条数据
                put("user_id", userInfo.userId)
                put("username", userInfo.username)
                put("user_type", userInfo.userType.name)
                put("theme", userInfo.theme.name)
                put("is_auto_login", userInfo.isAutoLogin)
            }
            if (db.insert(tableName, null, userInfoEntity) == -1L) {
                Toast.makeText(context, "SQLite数据库插入失败", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "SQLite数据库插入时发生异常", Toast.LENGTH_SHORT).show()
        }
    }

    fun select(context: Context, tableName: String = "user_info", userId: Int): UserInfo? {
        return select(context, userIdStr = userId.toString())
    }

    fun select(context: Context, tableName: String = "user_info", userIdStr: String): UserInfo? {
        try {
            val dbHelper = MyDbHelper(context, "novappro.db", Constant.DATABASE_VERSION)
            val db = dbHelper.writableDatabase
            val cursor =
                db.rawQuery("select * from user_info where user_id = ?;", arrayOf(userIdStr))

            var index = cursor.getColumnIndex("user_id")
            val userId = cursor.getInt(index)
            index = cursor.getColumnIndex("username")
            val username = cursor.getString(index)
            index = cursor.getColumnIndex("user_type")
            val userType = UserType.valueOf(cursor.getString(index))
            index = cursor.getColumnIndex("theme")
            val theme = UserInfo.Theme.valueOf(cursor.getString(index))
            index = cursor.getColumnIndex("is_auto_login")
            val isAutoLogin = cursor.getString(index) == "true"

            cursor.close()

            return UserInfo(
                userId = userId,
                username = username,
                userType = userType,
                theme = theme,
                isAutoLogin = isAutoLogin
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "SQLite数据库查询时发生异常", Toast.LENGTH_SHORT).show()
            return null
        }
    }
}