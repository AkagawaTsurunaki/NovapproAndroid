package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.course

import android.os.Bundle
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
import com.github.akagawatsurunaki.android.novapproandroid.activity.admin.course.ui.theme.NovapproAndroidTheme
import com.github.akagawatsurunaki.android.novapproandroid.databinding.AddCourseLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.Course
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.CourseService
import java.math.BigDecimal

class AddCourseActivity : ComponentActivity() {

    private lateinit var binding: AddCourseLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_course_layout)
        binding = AddCourseLayoutBinding.inflate(layoutInflater)

        binding.buttonConfirmAddCourse.setOnClickListener {
            addCourse()
        }

        binding.buttonCancelAddCourse.setOnClickListener {
            finish()
        }
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

        if (serviceResult.first.level != ServiceMessage.Level.SUCCESS) {
            Toast.makeText(this, serviceResult.first.message, Toast.LENGTH_LONG).show()
        } else {
            finish()
        }

    }
}
