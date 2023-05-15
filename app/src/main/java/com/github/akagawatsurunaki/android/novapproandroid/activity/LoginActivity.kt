package com.github.akagawatsurunaki.android.novapproandroid.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.LoginLayoutBinding

class LoginActivity : ComponentActivity() {

    private lateinit var binding: LoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置布局
        setContentView(R.layout.login_layout)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        val userId = binding.loginLayoutEditTextUserId.text.toString()
        val password = binding.loginLayoutEditTextPassword.text.toString()
    }
}
