package com.github.akagawatsurunaki.android.novapproandroid.model

import java.util.Date

data class CourseApplication(
    /**
     * CourseApplication所从属的审批流的唯一标志号码
     */
    var flowNo: String?,

    /**
     * CourseApplication的申请人的ID
     */
    var addUserId: Int?,

    /**
     * CourseApplication的申请时间, 即创建的日期
     */
    var addTime: Date?,

    /**
     * 课程申请CourseApplication的课程代码, 可能包含了多个课程代码
     * @implNote 本字段必须实现由String List转化为String的方法
     */
    var approCourses: String?
)
