package com.github.akagawatsurunaki.android.novapproandroid.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.config.Config
import com.github.akagawatsurunaki.android.novapproandroid.databinding.LoginLayoutBinding
import java.net.HttpURLConnection
import java.net.URL

class LoginActivity : ComponentActivity() {

    private lateinit var binding: LoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置布局
        setContentView(R.layout.login_layout)
        binding = LoginLayoutBinding.inflate(layoutInflater)

        binding.loginLayoutButtonLogin.setOnClickListener {
            val userId = binding.loginLayoutEditTextUserId.text.toString()
            val password = binding.loginLayoutEditTextPassword.text.toString()
            val queue = Volley.newRequestQueue(this)
////            val url = Config.prefix + "/android/login?userId=${userId}&rawPassword=${password}"
            val url = Config.prefix + "/android/login"

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    // Display the first 500 characters of the response string.
                    Log.d("response value", response.substring(0, 500))
                },
                { Log.e("response error", "发生错误") })

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }

    }
}
