package com.github.akagawatsurunaki.android.novapproandroid.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDbHelper (private val context: Context, name: String, version: Int) :

    SQLiteOpenHelper(context, name, null, version) {

    private val TAG = "MyDbHelper"
    private val createUserInfo = "create table user_info (" +
            "userId integer primary key," +
            "username text," +
            "usertype text," +
            "autoLogin text,"+
            "theme text)"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createUserInfo)
        Log.i(TAG, "数据库初始化完成")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}