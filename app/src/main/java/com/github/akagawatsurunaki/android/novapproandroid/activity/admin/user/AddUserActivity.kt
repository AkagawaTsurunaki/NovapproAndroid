package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.user

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.AddUserLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.service.manage.UserManageService
import com.github.akagawatsurunaki.android.novapproandroid.util.MenuHandler
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil

class AddUserActivity : AppCompatActivity() {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        MenuHandler.check(this, item)
        return super.onOptionsItemSelected(item)
    }

    private fun addUser() {
        val username = binding.textViewUserUsername.text.toString()
        val userType = enumValues<UserType>()[binding.radioGroupUserType.checkedRadioButtonId]
        val user = User(0, username, null, userType)
        val addUser = UserManageService.addUser(user)
        ServiceResultUtil.show(this, addUser.first)
    }

}
