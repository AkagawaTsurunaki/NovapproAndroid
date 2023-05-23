package com.github.akagawatsurunaki.android.novapproandroid.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDbHelper (private val context: Context, name: String, version: Int) :

    SQLiteOpenHelper(context, name, null, version) {

    private val TAG = "MyDbHelper"
    private val createUserInfo = "create table user_info (" +
            "user_id integer primary key," +
            "username text," +
            "user_type text)"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createUserInfo)
        Log.i(TAG, "数据库初始化完成")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}