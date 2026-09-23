package com.example.omshivgoraksha.data.model.lesson.request

data class LessonRequest(

    val lessonName: String,

    val thumbnail: String,

    val duration: String,

    val description: String,

    val courseId: Long
)