package com.github.akagawatsurunaki.android.novapproandroid.model

import com.github.akagawatsurunaki.android.novapproandroid.enumeration.ApprovalStatus
import java.util.Date

data class ApplicationItem(
    var flowNo: String? = null,

    var title: String? = null,

    var applicantName: String? = null,

    var approverName: String? = null,

    var addTime: Date? = null,

    var approvalStatus: ApprovalStatus? = null
) {}
