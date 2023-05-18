package com.github.akagawatsurunaki.android.novapproandroid.activity.admin

import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
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
import com.github.akagawatsurunaki.android.novapproandroid.activity.admin.ui.theme.NovapproAndroidTheme
import com.github.akagawatsurunaki.android.novapproandroid.databinding.UserManagementLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.service.manage.UserManageService

class UserManagementActivity : ComponentActivity() {

    private lateinit var binding:UserManagementLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_management_layout)
        binding = UserManagementLayoutBinding.inflate(layoutInflater)

        val getAllUsersServiceResult = UserManageService.getAllUsers()

        if (getAllUsersServiceResult.first.level != ServiceMessage.Level.SUCCESS) {
            Toast.makeText(this, getAllUsersServiceResult.first.message, Toast.LENGTH_LONG).show()
        }

        val allUsers = getAllUsersServiceResult.second ?: emptyList()
        allUsers.forEach {
            binding.tableLayoutAllUsers.addView(
                TableRow(this).apply {

                    addView(TextView(this@UserManagementActivity).apply {
                        text = it.id.toString()
                    })

                    addView(TextView(this@UserManagementActivity).apply {
                        text = it.username
                    })

                    addView(TextView(this@UserManagementActivity).apply {
                        text = it.type!!.chinese
                    })
                    setOnClickListener { _ ->
                        toMessageBox(it.id)
                    }
                }
            )
        }

        binding.textViewAddUser.setOnClickListener {
            toAddUserMsgBox()
        }

    }

    private fun toAddUserMsgBox() {
        TODO("Not yet implemented")
    }

    // 弹出消息框，可以直接修改，也可以直接点击删除，
    private fun toMessageBox(id: Int?) {
        TODO("Not yet implemented")
    }
}
