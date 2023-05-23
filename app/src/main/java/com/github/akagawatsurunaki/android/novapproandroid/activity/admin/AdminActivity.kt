package com.github.akagawatsurunaki.android.novapproandroid.activity.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.activity.admin.approvalflow.ApprovalFlowQueryActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.admin.course.CourseManagementActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.admin.user.UserManagementActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.stu.StudentActivity
import com.github.akagawatsurunaki.android.novapproandroid.databinding.AdminLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.util.MenuHandler

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: AdminLayoutBinding

    private val functionalityList =
        listOf(
            "系统用户管理",
            "课申请课程管理",
            "用户审批权限管理",
            "审批记录查询"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = AdminLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, functionalityList)
        binding.listViewAdminFunctionalities.adapter = adapter
        binding.listViewAdminFunctionalities.setOnItemClickListener { _, _, position, _ ->
            when (functionalityList[position]) {
                "系统用户管理" -> toSystemUserManagementActivity()
                "课申请课程管理" -> toCourseManagementActivity()
                "用户审批权限管理" -> toUserManagementActivity()
                "审批记录查询" -> toApprovalFlowQueryActivity()
                else -> Log.e(
                    StudentActivity::class.java.name,
                    "onCreate: 这是一个不可能被点击到的位置",
                )
            }
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
    private fun toSystemUserManagementActivity() {
        startActivity(Intent(this, UserManagementActivity::class.java))
    }

    private fun toCourseManagementActivity() {
        startActivity(Intent(this, CourseManagementActivity::class.java))
    }

    private fun toUserManagementActivity() {
        startActivity(Intent(this, UserManagementActivity::class.java))
    }

    private fun toApprovalFlowQueryActivity() {
    startActivity(Intent(this, ApprovalFlowQueryActivity::class.java))
    }

}
