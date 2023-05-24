package com.github.akagawatsurunaki.android.novapproandroid.service

import com.github.akagawatsurunaki.android.novapproandroid.model.ApprovalFlow
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.util.ResponseUtil

object SearchService {
    fun getPageAsStudentView(
        page: String,
        search: String,
        userId: String
    ): Pair<ServiceMessage, List<ApprovalFlow>?> {
        return ResponseUtil.getServiceResult<List<ApprovalFlow>>(
            servletValue = "/android/searchService/getPageAsStudentView",
            mapOf("page" to page, "search" to search, "userId" to userId)
        )
    }

    fun getPageAsTeacherView(
        page: String,
        search: String,
    ): Pair<ServiceMessage, List<ApprovalFlow>?> {
        return ResponseUtil.getServiceResult<List<ApprovalFlow>>(
            servletValue = "/android/searchService/getPageAsTeacherView",
            mapOf("page" to page, "search" to search)
        )
    }
}