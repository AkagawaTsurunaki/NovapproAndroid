package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.approvalflow

import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.ApprovalFlowQueryLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.service.appro.ApprovalService
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil


class ApprovalFlowQueryActivity : ComponentActivity() {

    private lateinit var binding: ApprovalFlowQueryLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.approval_flow_query_layout)
        binding = ApprovalFlowQueryLayoutBinding.inflate(layoutInflater)

        val getAllApprovalFlowsServiceResult = ApprovalService.getAllApprovalFlows()
        if (ServiceResultUtil.isSuccess(this, getAllApprovalFlowsServiceResult.first)) {
            val allApprovalFlows = getAllApprovalFlowsServiceResult.second ?: emptyList()
            allApprovalFlows.forEach {
                binding.tableLayoutAllApprovalFlows.addView(
                    TableRow(this).apply {
                        addView(
                            TextView(this@ApprovalFlowQueryActivity).apply {
                                text = it.flowNo
                            }
                        )
                        addView(
                            TextView(this@ApprovalFlowQueryActivity).apply {
                                text = it.approStatus!!.chinese
                            }
                        )
                        addView(
                            TextView(this@ApprovalFlowQueryActivity).apply {
                                text = it.title
                            }
                        )
                        addView(
                            TextView(this@ApprovalFlowQueryActivity).apply {
                                text = it.busType!!.chinese
                            }
                        )
                        addView(
                            TextView(this@ApprovalFlowQueryActivity).apply {
                                text = it.addUserId.toString()
                            }
                        )
                        addView(
                            TextView(this@ApprovalFlowQueryActivity).apply {
                                text = it.addTime.toString()
                            }
                        )
                        addView(
                            TextView(this@ApprovalFlowQueryActivity).apply {
                                text = it.remark
                            }
                        )
                    }
                )
            }
        }

    }
}