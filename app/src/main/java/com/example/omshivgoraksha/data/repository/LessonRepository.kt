package com.example.omshivgoraksha.data.repository

import com.example.omshivgoraksha.data.model.lesson.request.LessonRequest
import com.example.omshivgoraksha.data.model.lesson.response.LessonResponse
import com.example.omshivgoraksha.data.remote.ApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class LessonRepository(
    private val apiService: ApiService
) {

    // =========================================================
    // GET LESSONS
    // =========================================================

    suspend fun getLessonsByCourse(
        courseId: Long
    ): Result<List<LessonResponse>> {

        return try {

            val response =
                apiService.getLessonsByCourse(courseId)

            if (response.isSuccessful) {

                Result.success(
                    response.body() ?: emptyList()
                )

            } else {

                Result.failure(
                    Exception(
                        "Failed to load lessons. HTTP ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    // =========================================================
    // CREATE LESSON
    // =========================================================

    suspend fun createLesson(

        request: LessonRequest,

        documentFiles: List<File>,

        videoFile: File?

    ): Result<LessonResponse> {

        return try {

            if (videoFile == null) {

                return Result.failure(
                    Exception("Video file is required.")
                )
            }

            val response =
                apiService.createLesson(

                    lessonName =
                        request.lessonName.toPart(),

                    thumbnail =
                        request.thumbnail.toPart(),

                    duration =
                        request.duration.toPart(),

                    description =
                        request.description.toPart(),

                    documents =
                        documentFiles.map {
                            createDocumentPart(it)
                        },

                    videoName =
                        createVideoPart(videoFile),

                    courseId =
                        request.courseId
                            .toString()
                            .toPart()
                )


            if (response.isSuccessful) {

                response.body()?.let {

                    Result.success(it)

                } ?: Result.failure(
                    Exception("Empty response from server.")
                )

            } else {

                Result.failure(
                    Exception(
                        "Failed to create lesson. HTTP ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    // =========================================================
    // UPDATE LESSON
    // =========================================================

    suspend fun updateLesson(

        lessonId: Long,

        request: LessonRequest,

        documentFiles: List<File>,

        videoFile: File?

    ): Result<LessonResponse> {

        return try {

            val response =
                apiService.updateLesson(

                    lessonId = lessonId,

                    lessonName =
                        request.lessonName.toPart(),

                    thumbnail =
                        request.thumbnail.toPart(),

                    duration =
                        request.duration.toPart(),

                    description =
                        request.description.toPart(),

                    documents =
                        documentFiles.map {
                            createDocumentPart(it)
                        },

                    videoName =
                        videoFile?.let {
                            createVideoPart(it)
                        }
                            ?: createEmptyVideoPart(),

                    courseId =
                        request.courseId
                            .toString()
                            .toPart()
                )


            if (response.isSuccessful) {

                response.body()?.let {

                    Result.success(it)

                } ?: Result.failure(
                    Exception("Empty response from server.")
                )

            } else {

                Result.failure(
                    Exception(
                        "Failed to update lesson. HTTP ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    // =========================================================
    // DELETE LESSON
    // =========================================================

    suspend fun deleteLesson(
        lessonId: Long
    ): Result<Unit> {

        return try {

            val response =
                apiService.deleteLesson(lessonId)

            if (response.isSuccessful) {

                Result.success(Unit)

            } else {

                Result.failure(
                    Exception(
                        "Failed to delete lesson. HTTP ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    // =========================================================
    // TEXT PART
    // =========================================================

    private fun String.toPart(): RequestBody {

        return toRequestBody(
            "text/plain".toMediaType()
        )
    }


    // =========================================================
    // DOCUMENT PART
    // =========================================================

    private fun createDocumentPart(
        file: File
    ): MultipartBody.Part {

        val mimeType =
            when (file.extension.lowercase()) {

                "pdf" ->
                    "application/pdf"

                "doc" ->
                    "application/msword"

                "docx" ->
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"

                "xls" ->
                    "application/vnd.ms-excel"

                "xlsx" ->
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"

                "txt" ->
                    "text/plain"

                else ->
                    "application/octet-stream"
            }


        val requestFile =
            file.asRequestBody(
                mimeType.toMediaType()
            )


        return MultipartBody.Part.createFormData(
            "documents",
            file.name,
            requestFile
        )
    }


    // =========================================================
    // VIDEO PART
    // =========================================================

    private fun createVideoPart(
        file: File
    ): MultipartBody.Part {

        val mimeType =
            when (file.extension.lowercase()) {

                "mp4" ->
                    "video/mp4"

                "mkv" ->
                    "video/x-matroska"

                "avi" ->
                    "video/x-msvideo"

                "mov" ->
                    "video/quicktime"

                else ->
                    "application/octet-stream"
            }


        val requestFile =
            file.asRequestBody(
                mimeType.toMediaType()
            )


        return MultipartBody.Part.createFormData(
            "videoName",
            file.name,
            requestFile
        )
    }


    private fun createEmptyVideoPart(): MultipartBody.Part {

        val emptyBody =
            "".toRequestBody(
                "application/octet-stream".toMediaType()
            )

        return MultipartBody.Part.createFormData(
            "videoName",
            "",
            emptyBody
        )
    }
}