package com.github.akagawatsurunaki.android.novapproandroid.model

import com.github.akagawatsurunaki.android.novapproandroid.enumeration.ApprovalStatus
import java.util.Date


data class ApprovalFlowDetail(
    var id: Int? = null,
    var flowNo: String? = null,
    var auditUserId: Int? = null,
    var auditRemark: String? = null,
    var auditTime: Date? = null,
    var auditStatus: ApprovalStatus? = null
) {}