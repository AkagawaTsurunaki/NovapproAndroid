package com.github.akagawatsurunaki.android.novapproandroid.activity.stu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.databinding.CheckCourseApplicationsLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.ApplyCourseService
import java.text.SimpleDateFormat
import java.util.Locale

class CheckCourseApplicationsActivity : ComponentActivity() {

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
                        text = it.flowNo?.substring(20) +"  "
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

    private fun toActivity(flowNo: String?) {
        startActivity(
            Intent(this, CourseApplicationDetailActivity::class.java)
                .apply { putExtra("flowNo", flowNo) })
    }
}
