package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.course

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson2.JSONObject
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.ModifyCourseLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.Course
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.CourseService
import com.github.akagawatsurunaki.android.novapproandroid.util.MenuHandler
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil
import java.math.BigDecimal

class ModifyCourseActivity : AppCompatActivity() {

    private lateinit var binding: ModifyCourseLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = ModifyCourseLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)
        val course =
            JSONObject.parseObject(intent.getStringExtra("selectedCourse"), Course::class.java)

        if (course == null) {
            Toast.makeText(this, "发生错误：未选中任何一门课程", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // 确认修改按钮
        binding.buttonConfirmModifyCourse.setOnClickListener {
            updateCourse()
        }

        // 取消修改按钮
        binding.buttonCancelModifyCourse.setOnClickListener {
            finish()
        }

        // 删除课程按钮事件绑定, 弹出警告框
        binding.buttonDeleteCourse.setOnClickListener {
            AlertDialog.Builder(this).apply {
                title = "危险操作"
                setMessage("确定要删除此课程吗？")
                setPositiveButton("确认") { dialog, _ ->
                    // 处理确认按钮点击事件
                    val serviceResult = CourseService.deleteCourse(course.code!!)
                    ServiceResultUtil.show(this@ModifyCourseActivity, serviceResult.first)
                    dialog.dismiss()
                }
                setNegativeButton("取消") { dialog, _ ->
                    // 处理取消按钮点击事件
                    dialog.dismiss()
                }
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

    private fun updateCourse() {

        val course = Course(
            code = binding.textViewCourseCode.text.toString(),
            name = binding.textViewCourseName.text.toString(),
            credit = BigDecimal(binding.textViewCourseCredit.text.toString()),
            serialNumber = binding.textViewSerialNumber.text.toString(),
            teachers = binding.textViewTeachers.text.toString(),
            onlineContactWay = binding.textViewOnlineContactWay.text.toString(),
            comment = binding.textViewComment.text.toString()
        )

        val serviceResult = CourseService.updateCourse(course)

        if (serviceResult.first.messageLevel != Level.SUCCESS) {
            Toast.makeText(this, serviceResult.first.message, Toast.LENGTH_LONG).show()
        }

    }
}