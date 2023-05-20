package com.github.akagawatsurunaki.android.novapproandroid.activity.teacher

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.databinding.CheckApprovalDetailLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.service.appro.ApprovalService

class CheckApprovalDetailActivity : ComponentActivity() {

    private lateinit var binding: CheckApprovalDetailLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = CheckApprovalDetailLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        val flowNo = intent.getStringExtra("flowNo")
        val loginUserId = intent.getStringExtra("loginUserId")

        flowNo?.let {
            loginUserId?.let {
                val getCourseApplicationItemDetailServiceResult =
                    ApprovalService.getCourseApplicationItemDetail(flowNo, loginUserId)

                if (getCourseApplicationItemDetailServiceResult.first.messageLevel != Level.SUCCESS) {
                    Toast.makeText(
                        this,
                        getCourseApplicationItemDetailServiceResult.first.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                binding.buttonApprovalAgree.setOnClickListener {
                    callService(flowNo, true)
                }

                binding.buttonApprovalRefuse.setOnClickListener {
                    callService(flowNo, false)
                }

            }
        }
    }

    private fun callService(flowNo: String, isAgree: Boolean) {
        val saveApprovalResultServiceResult = ApprovalService.saveApprovalResult(
            flowNo = flowNo,
            remark = binding.editTextApprovalRemark.text.toString(),
            confirm = if (isAgree) "同意审批" else "驳回审批"
        )
        if (saveApprovalResultServiceResult.first.messageLevel != Level.SUCCESS) {
            Toast.makeText(this, saveApprovalResultServiceResult.first.message, Toast.LENGTH_LONG)
                .show()
        }
    }

}
