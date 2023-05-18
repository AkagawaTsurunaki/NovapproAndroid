package com.github.akagawatsurunaki.android.novapproandroid.activity.teacher

import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.TeacherLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.service.appro.ApprovalService

class TeacherActivity : ComponentActivity() {

    private lateinit var binding: TeacherLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_layout)
        binding = TeacherLayoutBinding.inflate(layoutInflater)

        val loginUserId = intent.getStringExtra("loginUserId")

        loginUserId?.let {
            val getApplicationItemsServiceResult = ApprovalService.getApplicationItems(loginUserId)

            if (getApplicationItemsServiceResult.first.level != ServiceMessage.Level.SUCCESS) {
                Toast.makeText(
                    this,
                    getApplicationItemsServiceResult.first.message,
                    Toast.LENGTH_LONG
                ).show()
                return@let
            }

            val applicationItems = getApplicationItemsServiceResult.second ?: emptyList()
            applicationItems.forEach {
                binding.tableLayoutApprovalItemTitle.addView(
                    TableRow(this).apply {

                        addView(TextView(this@TeacherActivity).apply {
                            text = it.flowNo
                        })

                        addView(TextView(this@TeacherActivity).apply {
                            text = it.title
                        })

                        addView(TextView(this@TeacherActivity).apply {
                            text = it.approverName
                        })

                        addView(TextView(this@TeacherActivity).apply {
                            text = it.addTime.toString()
                        })

                        addView(TextView(this@TeacherActivity).apply {
                            text = it.approvalStatus!!.chinese
                        })

                        setOnClickListener { _ ->
                            toActivity(it.flowNo!!)
                        }
                    }
                )
            }
        }
    }

    private fun toActivity(flowNo: String) {
        val intent = Intent(this, CheckApprovalDetailActivity::class.java).apply {
            putExtra("flowNo", flowNo)
        }
        startActivity(intent)
    }

}
