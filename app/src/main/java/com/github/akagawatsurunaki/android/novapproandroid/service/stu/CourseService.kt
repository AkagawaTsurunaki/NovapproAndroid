package com.github.akagawatsurunaki.android.novapproandroid.service.stu

import com.github.akagawatsurunaki.android.novapproandroid.model.Course
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.util.ResponseUtil

object CourseService {
    fun getCoursesCanBeApplied(): Pair<ServiceMessage, List<Course>?> {
        return ResponseUtil.getServiceResult<List<Course>>("/android/getCoursesCanBeApplied")
    }
}