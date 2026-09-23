package com.example.omshivgoraksha.data.model.course.request

data class CourseRequest(
    val courseName: String,
    val thumbnail: String,
    val duration: String,
    val description: String,
    val price: Long,
    val points: List<String>
)
