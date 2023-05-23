package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.course

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson2.JSONObject
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.CourseManagementLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.Course
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.CourseService
import com.github.akagawatsurunaki.android.novapproandroid.util.MenuHandler
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil

class CourseManagementActivity : AppCompatActivity() {
    private lateinit var binding: CourseManagementLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = CourseManagementLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        val getAllCoursesServiceResult = CourseService.getAllCourses()

        ServiceResultUtil.show(this, getAllCoursesServiceResult.first)

        val allCourses = getAllCoursesServiceResult.second ?: emptyList()

        allCourses.forEach {
            binding.tableLayoutAllCourses.addView(
                TableRow(this).apply {
                    addView(
                        TextView(this@CourseManagementActivity).apply {
                            text = it.code
                        }
                    )
                    addView(
                        TextView(this@CourseManagementActivity).apply {
                            text = it.name
                        }
                    )
                    addView(
                        TextView(this@CourseManagementActivity).apply {
                            text = it.serialNumber
                        }
                    )
                    addView(
                        TextView(this@CourseManagementActivity).apply {
                            text = it.teachers
                        }
                    )
                    addView(
                        TextView(this@CourseManagementActivity).apply {
                            text = it.onlineContactWay
                        }
                    )
                    addView(
                        TextView(this@CourseManagementActivity).apply {
                            text = it.comment
                        }
                    )
                    setOnClickListener { _ ->
                        toModifyCourseActivity(it)
                    }
                }
            )
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

    private fun toModifyCourseActivity(course: Course) {
        startActivity(Intent(this, ModifyCourseActivity::class.java).apply {
            putExtra("selectedCourse", JSONObject.toJSONString(course))
        })
    }
}

