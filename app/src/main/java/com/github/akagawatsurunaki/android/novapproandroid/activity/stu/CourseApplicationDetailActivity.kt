package com.github.akagawatsurunaki.android.novapproandroid.activity.stu

import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.databinding.CourseApplicationDetailLayoutBinding
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

        flowNo?.let {
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
            binding.tableLayoutApprovalFlowDetails.apply {
                addView(
                    TableRow(this@CourseApplicationDetailActivity).apply {
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.flowNo
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.title
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.applicantId.toString()
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.applicantName
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.addTime.toString()
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.approStatus!!.chinese
                        })
                        addView(TextView(this@CourseApplicationDetailActivity).apply {
                            text = it.applCourses.toString()
                        })
                    }
                )
            }
        }
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