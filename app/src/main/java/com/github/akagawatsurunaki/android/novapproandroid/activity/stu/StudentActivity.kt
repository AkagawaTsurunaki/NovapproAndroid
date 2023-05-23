package com.github.akagawatsurunaki.android.novapproandroid.activity.stu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.StudentLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.util.MenuHandler

class StudentActivity : AppCompatActivity() {

    private val functionalityList = listOf("我要申请课程", "查看我的申请")

    private lateinit var binding: StudentLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = StudentLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, functionalityList)
        binding.studentLayoutListViewFunctionalityList.adapter = adapter
        binding.studentLayoutListViewFunctionalityList.setOnItemClickListener { _, _, position, _ ->
            when (functionalityList[position]) {
                "我要申请课程" -> toApplyCourseActivity()
                "查看我的申请" -> toCheckCourseApplicationActivity()
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

    private fun toApplyCourseActivity() {
        val intent = Intent(this, ApplyCourseActivity::class.java)
        startActivity(intent)
    }

    private fun toCheckCourseApplicationActivity() {
        val intent = Intent(this, CheckCourseApplicationsActivity::class.java)
        startActivity(intent)
    }
}
