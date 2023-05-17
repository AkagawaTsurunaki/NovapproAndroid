package com.github.akagawatsurunaki.android.novapproandroid.model

import com.github.akagawatsurunaki.android.novapproandroid.enumeration.ApprovalStatus
import java.util.Date

data class CourseApplicationItemDetail(
    val flowNo: String?,

    val title: String?,

    val applicantId: Int?,

    val applicantName: String?,

    val addTime: Date?,

    val approStatus: ApprovalStatus?,

    val applCourses: List<Course>?
) {}