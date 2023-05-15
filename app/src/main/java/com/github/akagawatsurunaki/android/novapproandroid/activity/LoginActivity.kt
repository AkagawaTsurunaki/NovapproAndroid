package com.github.akagawatsurunaki.android.novapproandroid.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.config.Config
import com.github.akagawatsurunaki.android.novapproandroid.databinding.LoginLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.service.LoginService
import com.github.akagawatsurunaki.android.novapproandroid.util.ConnUtil
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.HttpURLConnection
import java.net.URL

class LoginActivity : ComponentActivity() {

    private lateinit var binding: LoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置布局
        setContentView(R.layout.login_layout)
        binding = LoginLayoutBinding.inflate(layoutInflater)

        ConnUtil.sendPostRequest(
            servletValue = "/android/login",
            mapOf("userId" to "20210002", "rawPassword" to "1234567890")
        )



        binding.loginLayoutButtonLogin.setOnClickListener {
            val userId = binding.loginLayoutEditTextUserId.text.toString()
            val password = binding.loginLayoutEditTextPassword.text.toString()
            LoginService.login(userId, password)
        }

    }
}
