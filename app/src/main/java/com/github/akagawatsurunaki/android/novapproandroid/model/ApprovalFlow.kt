package com.github.akagawatsurunaki.android.novapproandroid.model

import com.github.akagawatsurunaki.android.novapproandroid.enumeration.ApprovalStatus
import com.github.akagawatsurunaki.android.novapproandroid.enumeration.BusType
import java.util.Date

data class ApprovalFlow(
    /**
     * 审批流ApprovalFlow的唯一标志号码, 又名流水号
     */
    var flowNo: String?,

    /**
     * 审批流ApprovalFlow的状态
     *
     * @implNote 如果实现了LinearBus审批流程模式, 那么审批流ApprovalFlow的状态应与最后一个审批明细的状态一致
     */
    var approStatus: ApprovalStatus?,

    /**
     * 审批流ApprovalFlow的标题
     */
    var title: String?,

    /**
     * 审批流ApprovalFlow的总线类型
     *
     * @implNote LinearBus将审批流程组成一条直线, 即是一个线性流程图.
     */
    var busType: BusType?,

    /**
     * 审批流ApprovalFlow的创建人ID
     */
    var addUserId: Int?,

    /**
     * 审批流ApprovalFlow的创建时间
     */
    var addTime: Date?,

    /**
     * 申请人填写的申请原因
     */
    var remark: String?
) {}