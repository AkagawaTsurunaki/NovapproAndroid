package com.github.akagawatsurunaki.android.novapproandroid.util

import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.ContextCompat.startActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.activity.LoginActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.base.MyActivity
import com.github.akagawatsurunaki.android.novapproandroid.config.ActionCode

object MenuHandler {

    fun check(compatActivity: AppCompatActivity, item: MenuItem) {
        when (item.itemId) {
            R.id.menu_my -> {
                startActivity(compatActivity, Intent(compatActivity, MyActivity::class.java), null)
            }

            R.id.menu_change_theme -> {
                // 重新创建当前Activity，以应用新的主题
                changeTheme(compatActivity)
            }

            R.id.menu_logout -> {

                startActivity(compatActivity, Intent(compatActivity, LoginActivity::class.java).apply {
                    putExtra("actionCode", ActionCode.LOGOUT)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }, null)
            }
        }
    }

    private fun changeTheme(activity: AppCompatActivity) {
        AppCompatDelegate.setDefaultNightMode(if (AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES) MODE_NIGHT_NO else MODE_NIGHT_YES)
        Toast.makeText(activity, "切换主题成功", Toast.LENGTH_SHORT).show()
        activity.recreate()
    }
}