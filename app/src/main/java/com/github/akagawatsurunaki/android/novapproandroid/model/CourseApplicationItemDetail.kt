package com.github.akagawatsurunaki.android.novapproandroid.model

import com.github.akagawatsurunaki.android.novapproandroid.enumeration.ApprovalStatus
import java.util.Date

data class CourseApplicationItemDetail(
    var flowNo: String?,

    var title: String?,

    var applicantId: Int?,

    var applicantName: String?,

    var addTime: Date?,

    var approStatus: ApprovalStatus?,

    var applCourses: List<Course>?
) {}