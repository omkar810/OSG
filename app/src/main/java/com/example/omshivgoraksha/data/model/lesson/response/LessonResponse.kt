package com.example.omshivgoraksha.data.model.lesson.response

data class LessonResponse(

    val lessonId: Long? = null,

    val lessonName: String? = null,

    val thumbnail: String? = null,

    val duration: String? = null,

    val description: String? = null,

    val documents: List<String>? = null,

    val videoName: String? = null,

    val courseId: Long? = null
)