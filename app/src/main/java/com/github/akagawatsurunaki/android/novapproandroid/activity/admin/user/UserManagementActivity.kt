package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.alibaba.fastjson2.JSONObject
import com.github.akagawatsurunaki.android.novapproandroid.databinding.UserManagementLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.service.manage.UserManageService
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil

class UserManagementActivity : ComponentActivity() {

    private lateinit var binding: UserManagementLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = UserManagementLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)
        initTableLayout()
        binding.buttonAddUser.setOnClickListener {
            toAddUserActivity()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initTableLayout() {

        // 先移除所有的View组件
        binding.tableLayoutAllUsers.removeAllViews()


        val getAllUsersServiceResult = UserManageService.getAllUsers()

        if (ServiceResultUtil.isFailed(this, getAllUsersServiceResult.first)) {
            return
        }

        val allUsers = getAllUsersServiceResult.second ?: emptyList()
        allUsers.forEach {
            binding.tableLayoutAllUsers.addView(
                TableRow(this).apply {

                    addView(TextView(this@UserManagementActivity).apply {
                        text = it.id.toString() + "                        "
                    })

                    addView(TextView(this@UserManagementActivity).apply {
                        text = it.username + "                      "
                    })

                    addView(TextView(this@UserManagementActivity).apply {
                        text = it.type!!.chinese
                    })
                    setOnClickListener { _ ->
                        toModifyUserActivity(it)
                    }
                }
            )
        }
    }

    private fun toAddUserActivity() {
        startActivity(Intent(this, AddUserActivity::class.java))
    }

    override fun onRestart() {
        super.onRestart()
        initTableLayout()
    }

    // 弹出消息框，可以直接修改，也可以直接点击删除
    private fun toModifyUserActivity(selectedUser: User) {
        startActivity(Intent(this, ModifyUserActivity::class.java).apply {
            putExtra("selectedUser", JSONObject.toJSONString(selectedUser).toString())
        })
    }
}
