package com.github.akagawatsurunaki.android.novapproandroid.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.compose.material3.contentColorFor
import androidx.core.content.ContextCompat.startActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.activity.LoginActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.base.MyActivity
import com.github.akagawatsurunaki.android.novapproandroid.config.ActionCode

object MenuUtil {

    fun check(context: Context, item: MenuItem) {
        when (item.itemId) {
            R.id.menu_my -> {
                startActivity(context, Intent(context, MyActivity::class.java), null)
            }

            R.id.menu_change_theme -> {
                // 重新创建当前Activity，以应用新的主题
                changeTheme(context as AppCompatActivity)
//                context.startActivity(Intent(context, context.javaClass).apply {
//                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                })
            }

            R.id.menu_logout -> {
                startActivity(context, Intent(context, LoginActivity::class.java).apply {
                    putExtra("actionCode", ActionCode.LOGOUT)
                }, null)
            }
        }
    }

    fun changeTheme(activity: AppCompatActivity) {
        AppCompatDelegate.setDefaultNightMode(if (AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES) MODE_NIGHT_NO else MODE_NIGHT_YES)
        Toast.makeText(activity, "切换主题成功", Toast.LENGTH_SHORT).show()
        activity.recreate()
    }
}