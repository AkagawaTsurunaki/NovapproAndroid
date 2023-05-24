package com.github.akagawatsurunaki.android.novapproandroid.activity.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import com.github.akagawatsurunaki.android.novapproandroid.constant.Constant
import com.github.akagawatsurunaki.android.novapproandroid.databinding.SearchLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.ApprovalFlow
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.service.SearchService
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: SearchLayoutBinding

    private lateinit var actionCode: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = SearchLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        actionCode = intent.getStringExtra("actionCode") ?: "STUDENT"

        binding.buttionSearch.setOnClickListener {
            initTableLayout()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initTableLayout() {
        val service = callService(actionCode)

        if (ServiceResultUtil.isSuccess(this, service.first)) {
            val approvalFlows = service.second ?: emptyList()
            binding.tableLayoutSearch.removeAllViews()
            approvalFlows.forEach {
                binding.tableLayoutSearch.addView(
                    TableRow(this).apply {
                        addView(
                            TextView(this@SearchActivity).apply {
                                text =
                                    "${it.flowNo.toString()} |\n " +
                                            "${it.addTime.toString()} |\n " +
                                            "${it.addUserId.toString()} |\n " +
                                            "${it.title.toString()} |\n" +
                                            "${it.approStatus?.chinese}"
                            }
                        )
                    }
                )
            }
        }
    }


    private fun callService(
        actionCode: String,
    ): Pair<ServiceMessage, List<ApprovalFlow>?> {

        val page = binding.editTextPage.text.toString()
        val search = binding.editTextSearch.text.toString()
        val userId = Constant.loginUserId

        return if (actionCode == "STUDENT") {
            SearchService.getPageAsStudentView(page, search, userId.toString())
        } else {
            SearchService.getPageAsTeacherView(page, search)
        }

    }
}