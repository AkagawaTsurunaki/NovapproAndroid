package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.user

import android.app.AlertDialog
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.alibaba.fastjson2.JSONObject
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.ModifyUserLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.service.manage.UserManageService
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil

class ModifyUserActivity : ComponentActivity() {

    private lateinit var binding:ModifyUserLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_user_layout)
        binding  = ModifyUserLayoutBinding.inflate(layoutInflater)

        val user =
            JSONObject.parseObject(intent.getStringExtra("selectedUser"), User::class.java)

        if (user == null) {
            Toast.makeText(this, "发生错误：未选中任何一个用户", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        enumValues<UserType>().forEach {
            binding.radioGroupUserType.addView(
                RadioButton(this).apply {
                    text = it.chinese
                }
            )
        }

        // 确认修改按钮
        binding.buttonConfirmModifyUser.setOnClickListener {
            updateUser()
        }

        // 取消修改按钮
        binding.buttonCancelModifyUser.setOnClickListener {
            finish()
        }

        // 重置密码按钮
        binding.buttonResetPassword.setOnClickListener {
            AlertDialog.Builder(this).apply {
                title = "危险操作"
                setMessage("确定要重置此用户的密码吗？")
                setPositiveButton("确认（密码被重置为学号）") { dialog, _ ->
                    // 处理确认按钮点击事件
                    val serviceResult = UserManageService.resetUserPassword(user.id.toString())
                    ServiceResultUtil.show(this@ModifyUserActivity, serviceResult.first)
                    dialog.dismiss()
                }
                setNegativeButton("取消") { dialog, _ ->
                    // 处理取消按钮点击事件
                    dialog.dismiss()
                }
            }
        }

        // 删除用户按钮事件绑定, 弹出警告框
        binding.buttonConfirmDeleteUser.setOnClickListener {
            AlertDialog.Builder(this).apply {
                title = "危险操作"
                setMessage("确定要删除此用户吗？")
                setPositiveButton("确认") { dialog, _ ->
                    // 处理确认按钮点击事件
                    val serviceResult = UserManageService.deleteUser(user.id.toString())
                    ServiceResultUtil.show(this@ModifyUserActivity, serviceResult.first)
                    dialog.dismiss()
                }
                setNegativeButton("取消") { dialog, _ ->
                    // 处理取消按钮点击事件
                    dialog.dismiss()
                }
            }
        }

    }

    private fun updateUser() {
        val userId = binding.textViewUserUsername.text.toString().toInt()
        val username = binding.textViewUserUsername.text.toString()
        val userType = enumValues<UserType>()[binding.radioGroupUserType.checkedRadioButtonId]

        val updateUserServiceResult = UserManageService.updateUser(User(userId, username, null, userType))

        if (updateUserServiceResult.first.level != ServiceMessage.Level.SUCCESS) {
            Toast.makeText(this, updateUserServiceResult.first.message, Toast.LENGTH_LONG).show()
        }
    }
}
