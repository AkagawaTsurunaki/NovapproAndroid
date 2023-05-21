package com.github.akagawatsurunaki.android.novapproandroid.activity.teacher

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.constant.Constant
import com.github.akagawatsurunaki.android.novapproandroid.databinding.CheckApprovalDetailLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.CourseApplicationItemDetail
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.service.appro.ApprovalService
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil

class CheckApprovalDetailActivity : ComponentActivity() {

    private lateinit var binding: CheckApprovalDetailLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = CheckApprovalDetailLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        val flowNo = intent.getStringExtra("flowNo") ?: ""
        val loginUserId = intent.getIntExtra("loginUserId", 0)

        val getCourseApplicationItemDetailServiceResult =
            ApprovalService.getCourseApplicationItemDetail(flowNo, loginUserId.toString())

        if (ServiceResultUtil.isFailed(this, getCourseApplicationItemDetailServiceResult.first)) {
            finish()
        }
        getCourseApplicationItemDetailServiceResult.second?.let { initCourseApplicationItemDetailView(it) }
        binding.buttonApprovalAgree.setOnClickListener {
            callService(flowNo, true)
        }

        binding.buttonApprovalRefuse.setOnClickListener {
            callService(flowNo, false)
        }
    }

    private fun initCourseApplicationItemDetailView(courseApplicationItemDetail: CourseApplicationItemDetail) {
        binding.textViewApprovalDetailFlowNo.text = courseApplicationItemDetail.flowNo
        binding.textViewApprovalDetailAddTime.text = courseApplicationItemDetail.addTime?.let {
            Constant.simpleDateFormat.format(
                it
            )
        }
        binding.textViewApprovalDetailApplicantId.text = courseApplicationItemDetail.applicantId.toString()
        binding.textViewApprovalDetailApplicantName.text = courseApplicationItemDetail.applicantName.toString()
        binding.textViewApprovalDetailCourseCodeAndName.text = courseApplicationItemDetail.applCourses?.run {
            get(0).code + " | " + get(0).name
        } ?: ""
        binding.textViewApprovalDetailTitle.text = courseApplicationItemDetail.title.toString()
        binding.textViewApprovalDetailApprovalStatus.text = courseApplicationItemDetail.approStatus?.chinese.toString()
    }

    private fun callService(flowNo: String, isAgree: Boolean) {
        val saveApprovalResultServiceResult = ApprovalService.saveApprovalResult(

            flowNo = flowNo,
            remark = binding.editTextApprovalRemark.text.toString(),
            confirm = if (isAgree) "同意审批" else "驳回审批"
        )
        if (ServiceResultUtil.isSuccess(this, saveApprovalResultServiceResult.first)) {
            finish()
        }
    }

}
