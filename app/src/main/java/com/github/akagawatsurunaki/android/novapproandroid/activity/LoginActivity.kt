package com.github.akagawatsurunaki.android.novapproandroid.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.activity.stu.StudentActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.teacher.TeacherActivity
import com.github.akagawatsurunaki.android.novapproandroid.databinding.LoginLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.model.User
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
            val loginServiceResult = LoginService.login(userId, password)
            // 获取User对象
            val loginUser = loginServiceResult.second
            if (loginServiceResult.first.level == ServiceMessage.Level.SUCCESS) {
                // 转到对应的界面
                toActivity(loginUser!!)
            } else {
                Toast.makeText(this, loginServiceResult.first.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun toActivity(user: User) {
        val intent: Intent = when (user.type!!) {
            UserType.ADMIN -> toAdminActivity()
            UserType.LECTURE_TEACHER -> toTeacherActivity()
            UserType.SUPERVISOR_TEACHER -> toTeacherActivity()
            UserType.STUDENT -> toStudentActivity()
        }.apply { putExtra("loginUserId", user.id) }
        startActivity(intent)
    }

    private fun toTeacherActivity() = Intent(this, TeacherActivity::class.java)
    private fun toAdminActivity() = Intent(this, AdminActivity::class.java)
    private fun toStudentActivity() = Intent(this, StudentActivity::class.java)
}
