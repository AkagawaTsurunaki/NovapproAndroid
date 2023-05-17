package com.github.akagawatsurunaki.android.novapproandroid.activity.stu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
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
import androidx.core.content.FileProvider
import com.github.akagawatsurunaki.android.novapproandroid.R
import com.github.akagawatsurunaki.android.novapproandroid.databinding.ApplyCourseLayoutBinding
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.ApplyCourseService
import com.github.akagawatsurunaki.android.novapproandroid.service.stu.CourseService
import java.io.File

class ApplyCourseActivity : ComponentActivity() {
    private val takePhoto = 1
    private lateinit var imageUri: Uri
    lateinit var outputImage: File
    private val binding = ApplyCourseLayoutBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.apply_course_layout)

        val coursesCanBeAppliedServiceMessage = CourseService.getCoursesCanBeApplied()

        if (coursesCanBeAppliedServiceMessage.first.level != ServiceMessage.Level.SUCCESS) {
            Toast.makeText(this, coursesCanBeAppliedServiceMessage.first.message, Toast.LENGTH_LONG).show()
            return
        }

        val coursesCanBeApplied = coursesCanBeAppliedServiceMessage.second ?: emptyList()

        // 根据可申请的课程进行动态radio button加载
        coursesCanBeApplied.forEach {
            binding.radioGroupCourses.addView(
                RadioButton(this).apply {
                    text = "${it::code} | ${it::name}"
                    id = coursesCanBeApplied.indexOf(it)
                }
            )
        }

        // 拍照按钮事件绑定
        binding.buttonTakePhoto.setOnClickListener {
            // 创建File对象，用于存储拍照后的图片
            outputImage = File(externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri =
                FileProvider.getUriForFile(
                    this,
                    "com.github.akagawatsurunaki.android.novappro.fileprovider",
                    outputImage
                )
            // 启动相机程序
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, takePhoto)
        }

        // 创建课程申请按钮事件绑定
        binding.buttonCreateCourseApplication.setOnClickListener {
            val remark = binding.applyCourseLayoutEditTextRemark.text.toString()

            val selectedCourseId = coursesCanBeApplied[binding.radioGroupCourses.checkedRadioButtonId].code

            if (selectedCourseId == null) {
                Toast.makeText(this, "您还没有选中一个课程", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val serviceMessage = ApplyCourseService.createCourseApplication(
                courseId = selectedCourseId, remark = remark, file = outputImage
            )
            if (serviceMessage.level != ServiceMessage.Level.SUCCESS) {
                Toast.makeText(this, serviceMessage.message, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "成功申请了一个课程", Toast.LENGTH_LONG).show()
            }
            // 完成申请后，返回至上一个页面
            finish()
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
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
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
            bitmap, 0, 0, bitmap.width, bitmap.height,
            matrix, true
        )
        bitmap.recycle() // 将不再需要的Bitmap对象回收
        return rotatedBitmap
    }

}