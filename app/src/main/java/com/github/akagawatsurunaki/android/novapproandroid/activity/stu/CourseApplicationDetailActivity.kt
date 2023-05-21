package com.github.akagawatsurunaki.android.novapproandroid.activity.stu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.CourseApplicationDetailLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.databinding.ModelApprovalFlowDetailLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.ApprovalFlowDetail
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.service.appro.ApprovalService
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.CourseApplicationDetailService

class CourseApplicationDetailActivity : ComponentActivity() {

    private lateinit var binding: CourseApplicationDetailLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = CourseApplicationDetailLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        val flowNo = intent.getStringExtra("flowNo")

        if (flowNo == null) {
            finish()
            return
        }

        flowNo.let {
            // 初始化界面
            initAppliedCourses(it)
            initApprovalFlowDetails(it)

            // 绑定按钮事件
            binding.buttonApprovalConfirm.setOnClickListener { _ ->
                ApprovalService.tryFinishApprovalFlow(it, "确定")
            }
        }
    }


    private fun initApprovalFlowDetails(flowNo: String) {
        // 检查服务响应
        val getApprovalFlowDetailsServiceMessage = ApprovalService.getApprovalFlowDetails(flowNo)
        if (getApprovalFlowDetailsServiceMessage.first.messageLevel != Level.SUCCESS) {
            Toast.makeText(
                this,
                getApprovalFlowDetailsServiceMessage.first.message,
                Toast.LENGTH_LONG
            )
                .show()
            return
        }

        // 动态加载申请流明细
        val approvalFlowDetails = getApprovalFlowDetailsServiceMessage.second ?: emptyList()
        approvalFlowDetails.forEach {
            binding.linearLayoutApprovalFlowDetail.addView(
                createApprovalFlowDetailView(binding.linearLayoutApprovalFlowDetail, it)
            )
        }
    }

    private fun createApprovalFlowDetailView(
        parent: ViewGroup,
        approvalFlowDetail: ApprovalFlowDetail
    ): View {
        val binding = ModelApprovalFlowDetailLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        binding.textViewApprovalFlowDetailId.text = approvalFlowDetail.id.toString()
        binding.textViewApprovalFlowDetailFlowNo.text = approvalFlowDetail.flowNo.toString()
        binding.textViewApprovalFlowDetailAuditRemark.text = approvalFlowDetail.auditRemark.toString()
        binding.textViewApprovalFlowDetailAuditStatus.text =
            approvalFlowDetail.auditStatus!!.chinese.toString()
        binding.textViewApprovalFlowDetailAuditTime.text = approvalFlowDetail.auditTime.toString()
        binding.textViewApprovalFlowDetailAuditUserId.text = approvalFlowDetail.auditUserId.toString()

        return binding.root
    }


    private fun initAppliedCourses(flowNo: String) {
        // 检查服务响应
        val getAppliedCoursesServiceMessage =
            CourseApplicationDetailService.getAppliedCourses(flowNo)
        if (getAppliedCoursesServiceMessage.first.messageLevel != Level.SUCCESS) {
            Toast.makeText(this, getAppliedCoursesServiceMessage.first.message, Toast.LENGTH_LONG)
                .show()
            return
        }

        // 动态加载已申请的课表
        val appliedCourses = getAppliedCoursesServiceMessage.second ?: emptyList()
        appliedCourses.forEach {
            binding.tableLayoutAppliedCourses.apply {
                addView(
                    TableRow(this@CourseApplicationDetailActivity).apply {
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.code
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.name
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.serialNumber
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.teachers
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.onlineContactWay
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.comment
                        })
                    }
                )
            }
        }
    }
}