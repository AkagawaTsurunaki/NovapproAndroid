package com.github.akagawatsurunaki.android.novapproandroid.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import com.github.akagawatsurunaki.android.novapproandroid.constant.Constant
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.helper.MyDbHelper
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.model.UserInfo
import java.lang.Exception

object SQLiteUtil {

    fun insert(context: Context, user: User) {
        try {
            val tableName: String = "user_info"
            val dbHelper = MyDbHelper(context, "novappro.db", Constant.DATABASE_VERSION)
            val db = dbHelper.writableDatabase
            val userInfoEntity = ContentValues().apply {
                // 开始组装第一条数据
                put("user_id", user.id)
                put("username", user.username.toString())
                put("user_type", user.type!!.chinese)
            }
            db.insert(tableName, null, userInfoEntity)
        } catch (_: SQLiteConstraintException) {
        }
    }

    fun select(context: Context, userIdStr: String): UserInfo? {
        try {
            val dbHelper = MyDbHelper(context, "novappro.db", Constant.DATABASE_VERSION)
            val db = dbHelper.writableDatabase
            val cursor =
                db.rawQuery("select * from user_info where user_id = ?;", arrayOf(userIdStr))

            cursor.moveToFirst()

            var index = cursor.getColumnIndex("user_id")
            val userId = cursor.getInt(index)
            index = cursor.getColumnIndex("username")
            val username = cursor.getString(index)
            index = cursor.getColumnIndex("user_type")
            val userType = cursor.getString(index)

            cursor.close()

            return UserInfo(userId.toString(), username, userType);
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "SQLite数据库查询时发生异常", Toast.LENGTH_SHORT).show()
            return null
        }
    }
}