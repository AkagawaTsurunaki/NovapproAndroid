package com.github.akagawatsurunaki.android.novapproandroid.activity.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson2.JSON
import com.github.akagawatsurunaki.android.novapproandroid.databinding.MyLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.UserInfo

class MyActivity : AppCompatActivity() {

    private lateinit var binding: MyLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = MyLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)
        binding.textViewUserId.text = intent.getIntExtra("loginUserId", -1).toString()
        binding.textViewUserUsername.text = intent.getStringExtra("loginUserName").toString()
        binding.textViewUserType.text = intent.getStringExtra("loginUserType").toString()
    }
}
