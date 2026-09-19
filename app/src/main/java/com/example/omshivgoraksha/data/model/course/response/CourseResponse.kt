package com.example.omshivgoraksha.data.model.course.response

data class CourseResponse(

    val id: Long? = null,

    val courseName: String? = null,

    val thumbnail: String? = null,

    val duration: String? = null,

    val description: String? = null,

    val price: Double? = null,

    val points: List<String>? = null,

    val documents: List<String>? = null
)