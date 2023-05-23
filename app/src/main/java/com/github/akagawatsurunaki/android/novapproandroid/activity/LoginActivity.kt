package com.github.akagawatsurunaki.android.novapproandroid.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.admin.AdminActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.stu.StudentActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.teacher.TeacherActivity
import com.github.akagawatsurunaki.android.novapproandroid.config.ActionCode
import com.github.akagawatsurunaki.android.novapproandroid.constant.Constant
import com.github.akagawatsurunaki.android.novapproandroid.databinding.LoginLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.helper.MyDbHelper
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.service.LoginService
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil


class LoginActivity : ComponentActivity() {

    private lateinit var binding: LoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = LoginLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        // 获取ActionCode

        when (intent.getIntExtra("actionCode", ActionCode.INVALID_ACTION)) {
            ActionCode.INVALID_ACTION -> {
                Toast.makeText(this, "无效动作", Toast.LENGTH_SHORT).show()
                return
            }

            ActionCode.LOGOUT -> {
                //
            }
        }

        // 创建数据库
        val dbHelper = MyDbHelper(this, "novappro.db", Constant.DATABASE_VERSION)

        dbHelper.writableDatabase



        // 为按钮绑定事件
        binding.loginLayoutButtonLogin.setOnClickListener {
//             login()
            testLogin()
        }
    }

    private fun logout() {
        binding.loginLayoutEditTextUserId.setText("")
        binding.loginLayoutEditTextPassword.setText("")
        // TODO: 数据库清除用户登录数据
    }

    private fun testLogin() {
        // 登录
        val loginServiceResult = LoginService.login("20210009", "1234567890")
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
