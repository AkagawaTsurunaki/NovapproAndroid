package com.github.akagawatsurunaki.android.novapproandroid.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.admin.AdminActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.stu.StudentActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.teacher.TeacherActivity
import com.github.akagawatsurunaki.android.novapproandroid.databinding.LoginLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.service.LoginService
import com.github.akagawatsurunaki.android.novapproandroid.util.ConnUtil
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil
import com.github.akagawatsurunaki.android.novapproandroid.util.SessionUtil


class LoginActivity : ComponentActivity() {

    private lateinit var binding: LoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = LoginLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        // 为按钮绑定事件
        binding.loginLayoutButtonLogin.setOnClickListener {
            // login()
            testLogin()
        }
    }

    private fun testLogin() {
        // 登录
        val loginServiceResult = LoginService.login("20210002", "1234567890")
        // 获取User对象
        val loginUser = loginServiceResult.second
        if (ServiceResultUtil.isSuccess(this, loginServiceResult.first)) {
            // 转到对应的界面
            toActivity(loginUser!!)
        }
    }

    private fun login() {
        val userId = binding.loginLayoutEditTextUserId.text.toString()
        val password = binding.loginLayoutEditTextPassword.text.toString()
        // 登录
        val loginServiceResult = LoginService.login(userId, password)
        // 获取User对象
        val loginUser = loginServiceResult.second
        if (ServiceResultUtil.isSuccess(this, loginServiceResult.first)) {
            // 转到对应的界面
            toActivity(loginUser!!)
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
