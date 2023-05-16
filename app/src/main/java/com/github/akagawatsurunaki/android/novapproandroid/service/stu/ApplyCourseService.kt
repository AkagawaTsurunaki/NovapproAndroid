package com.github.akagawatsurunaki.android.novapproandroid.service.stu

import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.util.ResponseUtil
import java.io.File

object ApplyCourseService {

    fun apply(courseId: String, remark: String, file: File): ServiceMessage {
        // TODO(to test here, idk there is an error or not)
        return ResponseUtil.getServiceMessage(
            servletValue = "/android/createCourseApplication",
            params = mapOf("selected_course[]" to courseId, "remark" to remark),
            fileParams = mapOf("upload_img" to file)
        )
    }
}