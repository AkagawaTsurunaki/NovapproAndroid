package com.github.akagawatsurunaki.android.novapproandroid.activity.stu

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.github.akagawatsurunaki.android.novapproandroid.databinding.ApplyCourseLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.Level
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.ApplyCourseService
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.CourseService
import com.github.akagawatsurunaki.android.novapproandroid.util.ServiceResultUtil
import java.io.File

class ApplyCourseActivity : ComponentActivity() {
    private val takePhoto = 1
    private lateinit var imageUri: Uri
    lateinit var outputImage: File
    private lateinit var binding: ApplyCourseLayoutBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化绑定对象
        binding = ApplyCourseLayoutBinding.inflate(layoutInflater)
        // 设置布局
        setContentView(binding.root)

        val coursesCanBeAppliedServiceMessage = CourseService.getCoursesCanBeApplied()

        if (coursesCanBeAppliedServiceMessage.first.messageLevel != Level.SUCCESS) {
            Toast.makeText(this, coursesCanBeAppliedServiceMessage.first.message, Toast.LENGTH_LONG)
                .show()
            return
        }

        val coursesCanBeApplied = coursesCanBeAppliedServiceMessage.second ?: emptyList()

        // 根据可申请的课程进行动态radio button加载
        coursesCanBeApplied.forEach {
            binding.radioGroupCourses.addView(RadioButton(this).apply {
                text = "${it.code.toString()} | ${it.name.toString()}"
                id = coursesCanBeApplied.indexOf(it)
            })
        }

        // 拍照按钮事件绑定
        binding.buttonTakePhoto.setOnClickListener {
            // 创建File对象，用于存储拍照后的图片
            outputImage = File(externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = FileProvider.getUriForFile(
                this,
                "com.github.akagawatsurunaki.android.novapproandroid.fileProvider",
                outputImage
            )
            // 启动相机程序, 这里会进行索取权限
            requestCameraPermission()
        }

        // 创建课程申请按钮事件绑定
        binding.buttonCreateCourseApplication.setOnClickListener {
            val checkedRadioButtonId = binding.radioGroupCourses.checkedRadioButtonId

            if (checkedRadioButtonId == -1) {
                Toast.makeText(this, "您必须选择1门课程", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // 如果没有被初始化
            if (!this::outputImage.isInitialized) {
                Toast.makeText(this, "您必须上传1张证明图片", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val remark = binding.applyCourseLayoutEditTextRemark.text.toString()

            if (remark.isBlank()) {
                Toast.makeText(this, "您必须填写申请理由", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val serviceMessage = ApplyCourseService.createCourseApplication(
                courseId = coursesCanBeApplied[checkedRadioButtonId].code ?: "",
                remark = remark,
                file = outputImage
            )

            if (ServiceResultUtil.isSuccess(this, serviceMessage)) {
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
                    // 将拍摄的照片显示出来
                    val bitmap = BitmapFactory.decodeStream(
                        contentResolver.openInputStream(imageUri)
                    )
                    binding.imageViewPhoto.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
        }
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
        )
        bitmap.recycle() // 将不再需要的Bitmap对象回收
        return rotatedBitmap
    }

    private val CAMERA_PERMISSION_REQUEST_CODE = 100

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 如果没有相机权限，弹出权限申请对话框
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            // 如果已经有相机权限，直接打开相机
            openCamera()
        }
    }

    private fun openCamera() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, takePhoto)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予了相机权限，可以打开相机了
                openCamera()
            } else {
                // 用户拒绝了相机权限，可以弹出提示框或做其他处理
                Toast.makeText(this, "没有相机权限，无法打开相机", Toast.LENGTH_SHORT).show()
            }
        }
    }


}