package com.github.akagawatsurunaki.android.novapproandroid.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.activity.stu.ApplyCourseActivity
import com.github.akagawatsurunaki.android.novapproandroid.activity.stu.CheckCourseApplicationsActivity
import com.github.akagawatsurunaki.android.novapproandroid.databinding.StudentLayoutBinding

class StudentActivity : ComponentActivity() {

    private val functionalityList = listOf("我要申请课程", "查看我的申请")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_layout)
        val binding = StudentLayoutBinding.inflate(layoutInflater)

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, functionalityList)
        binding.studentLayoutListViewFunctionalityList.adapter = adapter
        binding.studentLayoutListViewFunctionalityList.setOnItemClickListener { _, _, position, _ ->
            when (functionalityList[position]) {
                "我要申请课程" -> toApplyCourseActivity()
                "查看我的申请" -> toCheckCourseApplicationActivity()
                else -> Log.e(StudentActivity::class.java.name, "onCreate: 这是一个不可能被点击到的位置", )
            }
        }

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
