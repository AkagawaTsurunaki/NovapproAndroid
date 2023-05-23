package com.github.akagawatsurunaki.android.novapproandroid.activity.admin.approvalflow

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.ApprovalFlowQueryLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.service.appro.ApprovalService
import com.github.akagawatsurunaki.android.novapproandroid.util.MenuHandler
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil


class ApprovalFlowQueryActivity : AppCompatActivity() {

    private lateinit var binding: ApprovalFlowQueryLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = ApprovalFlowQueryLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        MenuHandler.check(this, item)
        return super.onOptionsItemSelected(item)
    }
}