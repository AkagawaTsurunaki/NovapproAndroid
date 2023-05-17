package com.github.akagawatsurunaki.android.novapproandroid.activity.teacher

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.activity.teacher.ui.theme.NovapproAndroidTheme
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.service.appro.ApprovalService

class CheckApprovalDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_approval_detail_layout)

        val flowNo = intent.getStringExtra("flowNo")
        val loginUserId = intent.getStringExtra("loginUserId")

        flowNo?.let {
            loginUserId?.let {
                val getCourseApplicationItemDetailServiceResult =
                    ApprovalService.getCourseApplicationItemDetail(flowNo, loginUserId)

                if (getCourseApplicationItemDetailServiceResult.first.level != ServiceMessage.Level.SUCCESS) {
                    Toast.makeText(this, getCourseApplicationItemDetailServiceResult.first.message, Toast.LENGTH_LONG).show()
                }

                // TODO(进一步完成审批通过.拒绝界面)

            }
        }


    }
}
