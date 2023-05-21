package com.github.akagawatsurunaki.android.novapproandroid.activity.teacher

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.constant.Constant
import com.github.akagawatsurunaki.android.novapproandroid.databinding.ModelApplicationItemLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.databinding.TeacherLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.ApplicationItem
import com.github.akagawatsurunaki.android.novapproandroid.service.appro.ApprovalService
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil
import java.text.SimpleDateFormat
import java.util.Locale

class TeacherActivity : ComponentActivity() {



    private lateinit var binding: TeacherLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = TeacherLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        val loginUserId = intent.getIntExtra("loginUserId", 0)

        initApplicationItemView(loginUserId)
    }

    private fun toActivity(flowNo: String) {
        val intent = Intent(this, CheckApprovalDetailActivity::class.java).apply {
            putExtra("flowNo", flowNo)
        }
        startActivity(intent)
    }

    private fun initApplicationItemView(loginUserId: Int) {
        val getApplicationItemsServiceResult =
            ApprovalService.getApplicationItems(loginUserId.toString())

        if (ServiceResultUtil.isFailed(this, getApplicationItemsServiceResult.first)) {
            return
        }

        val applicationItems = getApplicationItemsServiceResult.second ?: emptyList()

        applicationItems.forEach {
            binding.linearLayoutApplicationItem.addView(
                createApplicationItemView(binding.linearLayoutApplicationItem, it).apply {
                    setOnClickListener { _ ->
                        toActivity(it.flowNo!!)
                    }
                }
            )
        }
    }

    private fun createApplicationItemView(
        parent: ViewGroup,
        applicationItem: ApplicationItem
    ): View {
        val binding = ModelApplicationItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        binding.textViewApplicationItemFlowNo.text = applicationItem.flowNo.toString()
        binding.textViewApplicationItemTitle.text = applicationItem.title.toString()
        binding.textViewApplicationApplicant.text = applicationItem.applicantName.toString()
        binding.textViewApplicationApprover.text = applicationItem.approverName.toString()
        binding.textViewApplicationAddTime.text =
            applicationItem.addTime?.let { Constant.simpleDateFormat.format(it) }
        binding.textViewApplicationApproStatus.text =
            applicationItem.approvalStatus?.chinese.toString()

        return binding.root
    }

}
