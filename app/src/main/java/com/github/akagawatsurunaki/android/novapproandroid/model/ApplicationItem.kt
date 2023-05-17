package com.github.akagawatsurunaki.android.novapproandroid.model

import com.github.akagawatsurunaki.android.novapproandroid.enumeration.ApprovalStatus
import java.util.Date

data class ApplicationItem(
    val flowNo: String? = null,

    val title: String? = null,

    val applicantName: String? = null,

    val approverName: String? = null,

    val addTime: Date? = null,

    val approvalStatus: ApprovalStatus? = null
) {}
