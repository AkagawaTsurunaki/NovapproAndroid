package com.github.akagawatsurunaki.android.novapproandroid.service.appro

import com.github.akagawatsurunaki.android.novapproandroid.model.ApplicationItem
import com.github.akagawatsurunaki.android.novapproandroid.model.ApprovalFlow
import com.github.akagawatsurunaki.android.novapproandroid.model.CourseApplicationItemDetail
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.util.ResponseUtil

object ApprovalService {

    fun getAllApprovalFlows(): Pair<ServiceMessage, List<ApprovalFlow>?> {
        return ResponseUtil.getServiceResult<List<ApprovalFlow>>(
            servletValue = "/android/approvalService/getAllApprovalFlows"
        )
    }

    fun getApplicationItem(flowNo: String): Pair<ServiceMessage, ApplicationItem?> {
        return ResponseUtil.getServiceResult<ApplicationItem>(
            servletValue = "/android/approvalService/getApplicationItem",
            mapOf("flowNo" to flowNo)
        )
    }

    fun getApplicationItems(): Pair<ServiceMessage, List<ApplicationItem>?> {
        return ResponseUtil.getServiceResult<List<ApplicationItem>>(
            servletValue = "/android/approvalService/getApplicationItems"
        )
    }

    fun getCourseApplicationItemDetail(flowNo: String): Pair<ServiceMessage, CourseApplicationItemDetail?> {
        return ResponseUtil.getServiceResult<CourseApplicationItemDetail>(
            servletValue = "/android/approvalService/getCourseApplicationItemDetail",
            mapOf("flowNo" to flowNo)
        )
    }

    fun saveApprovalResultServlet(flowNo: String, remark: String, confirm: String): Pair<ServiceMessage, CourseApplicationItemDetail?> {
        return ResponseUtil.getServiceResult<CourseApplicationItemDetail>(
            servletValue = "/android/approvalService/saveApprovalResult",
            mapOf("flowNo" to flowNo, "remark" to remark, "confirm" to confirm)
        )
    }

    fun getApprovalFlowDetails(flowNo: String) :Pair<ServiceMessage, List<CourseApplicationItemDetail>?> {
        return ResponseUtil.getServiceResult<List<CourseApplicationItemDetail>>(
            servletValue = "/android/approvalService/getApprovalFlowDetails",
            mapOf("flowNo" to flowNo)
        )
    }

    fun tryFinishApprovalFlow(flowNo: String, confirm: String) : ServiceMessage{
        return ResponseUtil.getServiceMessage(
            servletValue = "/android/approvalService/tryFinishApprovalFlow",
            mapOf("flowNo" to flowNo, "confirm" to confirm)
        )
    }


}