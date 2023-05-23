package com.github.akagawatsurunaki.android.novapproandroid.activity.stu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.CheckCourseApplicationsLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.ApplyCourseService
import com.github.akagawatsurunaki.android.novapproandroid.util.MenuHandler
import java.text.SimpleDateFormat
import java.util.Locale

class CheckCourseApplicationsActivity : AppCompatActivity() {

    private val dateFormat = SimpleDateFormat("M月d日", Locale.CHINA)

    private lateinit var binding: CheckCourseApplicationsLayoutBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = CheckCourseApplicationsLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        val getCourseApplicationsServiceMessage = ApplyCourseService.getCourseApplications()

        if (getCourseApplicationsServiceMessage.first.messageLevel != Level.SUCCESS) {
            Toast.makeText(
                this,
                getCourseApplicationsServiceMessage.first.message,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val courseApplications = getCourseApplicationsServiceMessage.second?.first ?: emptyList()
        val approvalStatuses = getCourseApplicationsServiceMessage.second?.second ?: emptyList()

        courseApplications.forEach {
            binding.tableLayoutCourseApplication.addView(
                TableRow(this).apply {
                    val index = courseApplications.indexOf(it)
                    addView(TextView(this@CheckCourseApplicationsActivity).apply {
                        text =  "..." +it.flowNo?.substring(23) +"  "
                    })
                    addView(TextView(this@CheckCourseApplicationsActivity).apply {
                        text = it.addUserId.toString()
                    })
                    addView(TextView(this@CheckCourseApplicationsActivity).apply {
                        text = "   " + dateFormat.format(it.addTime!!)
                    })
                    addView(TextView(this@CheckCourseApplicationsActivity).apply {
                        text = it.approCourses
                    })
                    addView(TextView(this@CheckCourseApplicationsActivity).apply {
                        text = approvalStatuses[index].chinese
                    })
                    setOnClickListener { _ ->
                        toActivity(it.flowNo)
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

    private fun toActivity(flowNo: String?) {
        startActivity(
            Intent(this, CourseApplicationDetailActivity::class.java)
                .apply { putExtra("flowNo", flowNo) })
    }
}
