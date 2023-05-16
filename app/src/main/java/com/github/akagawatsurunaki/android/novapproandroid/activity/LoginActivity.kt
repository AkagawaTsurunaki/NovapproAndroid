package com.github.akagawatsurunaki.android.novapproandroid.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.LoginLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.service.LoginService


class LoginActivity : ComponentActivity() {

    private lateinit var binding: LoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置布局
        setContentView(R.layout.login_layout)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        // 为按钮绑定事件
        binding.loginLayoutButtonLogin.setOnClickListener {
            val userId = binding.loginLayoutEditTextUserId.text.toString()
            val password = binding.loginLayoutEditTextPassword.text.toString()
            // 登录
            val serviceMessageUserPair = LoginService.login(userId, password)
            // 获取User对象
            val loginUser = serviceMessageUserPair.second
            if (serviceMessageUserPair.first.level == ServiceMessage.Level.SUCCESS) {
                // 登录成功的情况下，用户对象和其用户类型均不可能为null
                assert(loginUser?.type != null)
                // 转到对应的界面
                toActivity(loginUser!!.type!!)
            } else {
                Toast.makeText(this, serviceMessageUserPair.first.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun toActivity(userType: UserType) {
        when (userType) {
            UserType.ADMIN -> toAdminActivity()
            UserType.LECTURE_TEACHER -> toTeacherActivity()
            UserType.SUPERVISOR_TEACHER -> toTeacherActivity()
            UserType.STUDENT -> toStudentActivity()
        }
    }

    private fun toTeacherActivity() {
        val intent = Intent(this, TeacherActivity::class.java)
        startActivity(intent)
    }

    private fun toAdminActivity() {
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }

    private fun toStudentActivity() {
        val intent = Intent(this, StudentActivity::class.java)
        startActivity(intent)
    }
}
