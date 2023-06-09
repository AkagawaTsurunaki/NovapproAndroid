package com.github.akagawatsurunaki.android.novapproandroid.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.admin.AdminActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.stu.StudentActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.teacher.TeacherActivity
import com.github.akagawatsurunaki.android.novapproandroid.config.ActionCode
import com.github.akagawatsurunaki.android.novapproandroid.constant.Constant
import com.github.akagawatsurunaki.android.novapproandroid.databinding.LoginLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.helper.MyDbHelper
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.model.UserInfo
import com.github.akagawatsurunaki.android.novapproandroid.service.LoginService
import com.github.akagawatsurunaki.android.novapproandroid.util.SQLiteUtil
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginLayoutBinding
    private val TAG = "登录模块"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = LoginLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)
        // 创建数据库
        val dbHelper = MyDbHelper(this, "novappro.db", Constant.DATABASE_VERSION)
        dbHelper.writableDatabase

        // 为按钮绑定事件
        binding.loginLayoutButtonLogin.setOnClickListener {
             login()
        }

    }

    private fun saveUserInfo(user: User) {
        SQLiteUtil.insert(this, user = user)
    }

    private fun testLogin() {
        // 登录
        val loginServiceResult = LoginService.login("20210004", "1234567890")
        // 获取User对象
        val loginUser = loginServiceResult.second
        if (ServiceResultUtil.isSuccess(this, loginServiceResult.first)) {
            // 转到对应的界面
            saveUserInfo(loginUser!!)
            Constant.loginUserId = loginUser.id ?: 0
            toActivity(loginUser)
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
            saveUserInfo(loginUser!!)
            Constant.loginUserId = loginUser.id ?: 0
            toActivity(loginUser)
        }

    }

    private fun toActivity(user: User) {
        val intent: Intent = when (user.type!!) {
            UserType.ADMIN -> toAdminActivity()
            UserType.LECTURE_TEACHER -> toTeacherActivity()
            UserType.SUPERVISOR_TEACHER -> toTeacherActivity()
            UserType.STUDENT -> toStudentActivity()
        }.apply {
            putExtra("loginUserId", user.id)
            putExtra("loginUserName", user.username.toString())
            putExtra("loginUserType", user.type?.chinese.toString())
        }
        startActivity(intent)
    }

    private fun toTeacherActivity() = Intent(this, TeacherActivity::class.java)
    private fun toAdminActivity() = Intent(this, AdminActivity::class.java)
    private fun toStudentActivity() = Intent(this, StudentActivity::class.java)
}
