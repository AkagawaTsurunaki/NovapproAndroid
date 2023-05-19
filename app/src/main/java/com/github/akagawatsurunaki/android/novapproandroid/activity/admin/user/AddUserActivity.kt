package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.user

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.AddUserLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.service.manage.UserManageService
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil

class AddUserActivity : ComponentActivity() {

    private lateinit var binding: AddUserLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = AddUserLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        enumValues<UserType>().forEach {
            binding.radioGroupUserType.addView(
                RadioButton(this).apply {
                    text = it.chinese
                }
            )
        }

        binding.buttonConfirmAddUser.setOnClickListener {
            addUser()
        }

        binding.buttonCancelAddUser.setOnClickListener {
            finish()
        }
    }

    private fun addUser() {
        val username = binding.textViewUserUsername.text.toString()
        val userType = enumValues<UserType>()[binding.radioGroupUserType.checkedRadioButtonId]
        val user = User(0, username, null, userType)
        val addUser = UserManageService.addUser(user)
        ServiceResultUtil.show(this, addUser.first)
    }

}
