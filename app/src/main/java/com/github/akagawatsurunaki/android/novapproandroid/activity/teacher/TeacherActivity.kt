package com.github.akagawatsurunaki.android.novapproandroid.activity.teacher

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.constant.Constant
import com.github.akagawatsurunaki.android.novapproandroid.databinding.ModelApplicationItemLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.databinding.TeacherLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.ApplicationItem
import com.github.akagawatsurunaki.android.novapproandroid.service.appro.ApprovalService
import com.github.akagawatsurunaki.android.novapproandroid.util.MenuHandler
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil
import java.text.SimpleDateFormat
import java.util.Locale

class TeacherActivity : AppCompatActivity() {



    private lateinit var binding: TeacherLayoutBinding

    private var loginUserId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = TeacherLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)
        // 获取登录用户ID
        loginUserId = intent.getIntExtra("loginUserId", 0)

        initApplicationItemView(loginUserId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        MenuHandler.check(this, item)
        return super.onOptionsItemSelected(item)
    }


    override fun onRestart() {
        super.onRestart()
        initApplicationItemView(loginUserId)
    }

    private fun toActivity(flowNo: String) {
        val intent = Intent(this, CheckApprovalDetailActivity::class.java).apply {
            putExtra("flowNo", flowNo)
        }
        startActivity(intent)
    }

    private fun initApplicationItemView(loginUserId: Int) {
        binding.linearLayoutApplicationItem.removeAllViews()

        val getApplicationItemsServiceResult =
            ApprovalService.getApplicationItems(loginUserId.toString())

        if (ServiceResultUtil.isFailed(this, getApplicationItemsServiceResult.first)) {
            return
        }

        val applicationItems = getApplicationItemsServiceResult.second ?: emptyList()

        // 移除所有的内部组件


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
