package com.github.akagawatsurunaki.android.novapproandroid.service.stu

import com.github.akagawatsurunaki.android.novapproandroid.model.Course
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.util.ResponseUtil

object CourseApplicationDetailService {
    fun getAppliedCourses(flowNo: String): Pair<ServiceMessage, List<Course>?> {
        return ResponseUtil.getServiceResult<List<Course>>(
            servletValue = "/android/courseApplicationDetailService/getAppliedCourses",
            mapOf("flowNo" to flowNo)
        )
    }
}