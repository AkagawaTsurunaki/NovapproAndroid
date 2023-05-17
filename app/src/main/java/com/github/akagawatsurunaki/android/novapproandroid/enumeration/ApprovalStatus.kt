package com.github.akagawatsurunaki.android.novapproandroid.enumeration

enum class ApprovalStatus(val chinese: String?) {
    SUBMITTED("申请已提交"),
    LECTURE_TEACHER_EXAMING("课程主讲教师审批中"),
    SUPERVISOR_TEACHER_EXAMING("课程主管教师审批中"),
    APPROVED("审批成功"),
    REJECTED("审批驳回"),
    FINISHED("审批完成")
}