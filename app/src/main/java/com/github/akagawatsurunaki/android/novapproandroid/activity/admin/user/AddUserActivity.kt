package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.user

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.activity.admin.user.ui.theme.NovapproAndroidTheme
import com.github.akagawatsurunaki.android.novapproandroid.databinding.AddUserLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.UserType
import com.github.akagawatsurunaki.android.novapproandroid.model.User
import com.github.akagawatsurunaki.android.novapproandroid.service.manage.UserManageService
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil

class AddUserActivity : ComponentActivity() {

    private lateinit var binding: AddUserLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_user_layout)

        binding = AddUserLayoutBinding.inflate(layoutInflater)

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
