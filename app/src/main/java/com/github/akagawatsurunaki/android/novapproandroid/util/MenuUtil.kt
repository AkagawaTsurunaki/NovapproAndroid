package com.github.akagawatsurunaki.android.novapproandroid.util

import android.content.Context
import android.content.Intent
import android.view.MenuItem
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
                // TODO(切换主题)
            }
            R.id.menu_logout -> {
                startActivity(context, Intent(context, LoginActivity::class.java).apply {
                    putExtra("actionCode", ActionCode.LOGOUT)
                }, null)
            }
        }
    }
}