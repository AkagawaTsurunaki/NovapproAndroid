package com.github.akagawatsurunaki.android.novapproandroid.service.stu

import com.github.akagawatsurunaki.android.novapproandroid.enumeration.ApprovalStatus
import com.github.akagawatsurunaki.android.novapproandroid.model.CourseApplication
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.util.ResponseUtil
import java.io.File

object ApplyCourseService {

    fun createCourseApplication(courseId: String, remark: String, file: File): ServiceMessage {
        // TODO(to test here, idk there is an error or not)
        return ResponseUtil.getServiceMessage(
            servletValue = "/android/applyCourseService/createCourseApplication",
            params = mapOf("selected_course[]" to courseId, "remark" to remark),
            fileParams = mapOf("upload_img" to file)
        )
    }

    fun getCourseApplications(): Pair<ServiceMessage, Pair<List<CourseApplication>, List<ApprovalStatus>>?> {
        // TODO(to test here, idk there is an error or not)
        return ResponseUtil.getServiceResult<Pair<List<CourseApplication>, List<ApprovalStatus>>>(
            servletValue = "/android/applyCourseService/getCourseApplications")
    }
}