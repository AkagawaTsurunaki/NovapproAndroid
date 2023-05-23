package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.course

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.AddCourseLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.Course
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.CourseService
import com.github.akagawatsurunaki.android.novapproandroid.util.MenuHandler
import java.math.BigDecimal

class AddCourseActivity : AppCompatActivity() {

    private lateinit var binding: AddCourseLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = AddCourseLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        binding.buttonConfirmAddCourse.setOnClickListener {
            addCourse()
        }

        binding.buttonCancelAddCourse.setOnClickListener {
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

    private fun addCourse() {

        val course = Course(
            code = binding.textViewCourseCode.text.toString(),
            name = binding.textViewCourseName.text.toString(),
            credit = BigDecimal(binding.textViewCourseCredit.text.toString()),
            serialNumber = binding.textViewSerialNumber.text.toString(),
            teachers = binding.textViewTeachers.text.toString(),
            onlineContactWay = binding.textViewOnlineContactWay.text.toString(),
            comment = binding.textViewComment.text.toString()
        )

        val serviceResult = CourseService.addCourse(course)

        if (serviceResult.first.messageLevel != Level.SUCCESS) {
            Toast.makeText(this, serviceResult.first.message, Toast.LENGTH_LONG).show()
        } else {
            finish()
        }

    }
}
