package com.github.akagawatsurunaki.android.novapproandroid.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.TypeReference
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.LoginLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.service.LoginService
import com.github.akagawatsurunaki.android.novapproandroid.util.ConnUtil


class LoginActivity : ComponentActivity() {

    private lateinit var binding: LoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置布局
        setContentView(R.layout.login_layout)
        binding = LoginLayoutBinding.inflate(layoutInflater)

        Thread {
            val response = ConnUtil.sendPostRequest(
                servletValue = "/android/login",
                mapOf("userId" to "20210002", "rawPassword" to "1234567890")
            )

            val jsonString = response?.body?.string()

            if (jsonString != null) {
                val pairType = object : TypeReference<Pair<ServiceMessage, User>>() {}
                val serviceResult = JSONObject.parseObject(jsonString, pairType)
                Log.d("loginServiceResult", "onCreate: ${serviceResult.first.message}")
            } else {
                Log.e("Response Error", "Response body is empty!")
            }

        }.start()

        binding.loginLayoutButtonLogin.setOnClickListener {
            val userId = binding.loginLayoutEditTextUserId.text.toString()
            val password = binding.loginLayoutEditTextPassword.text.toString()
            LoginService.login(userId, password)
        }


    }
}
