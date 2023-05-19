package com.github.akagawatsurunaki.android.novapproandroid.service.stu

import com.alibaba.fastjson2.JSONObject
import com.github.akagawatsurunaki.android.novapproandroid.model.Course
import com.github.akagawatsurunaki.android.novapproandroid.model.ServiceMessage
import com.github.akagawatsurunaki.android.novapproandroid.util.ResponseUtil

object CourseService {
    fun getCoursesCanBeApplied(): Pair<ServiceMessage, List<Course>?> {
        return ResponseUtil.getServiceResult<List<Course>>(
            servletValue = "/android/courseService/getCoursesCanBeApplied"
        )
    }

    fun getAllCourses(): Pair<ServiceMessage, List<Course>?> {
        return ResponseUtil.getServiceResult<List<Course>>(
            servletValue = "/android/courseService/getAllCourses"
        )
    }

    fun deleteCourse(code: String): Pair<ServiceMessage, Course?> {
        return ResponseUtil.getServiceResult<Course>(
            servletValue = "/android/courseService/deleteCourse",
            mapOf("code" to code)
        )
    }

    fun updateCourse(course: Course): Pair<ServiceMessage, Course?> {
        return ResponseUtil.getServiceResult<Course>(
            servletValue = "/android/courseService/updateCourse",
            mapOf("course" to JSONObject.toJSONString(course))
        )
    }

    fun addCourse(course: Course): Pair<ServiceMessage, Course?> {
        return ResponseUtil.getServiceResult<Course>(
            servletValue = "/android/courseService/addCourse",
            mapOf("course" to JSONObject.toJSONString(course))
        )
    }
}