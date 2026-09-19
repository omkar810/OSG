package com.example.omshivgoraksha.data.repository

import com.example.omshivgoraksha.data.model.course.request.CourseRequest
import com.example.omshivgoraksha.data.model.course.response.CourseResponse
import com.example.omshivgoraksha.data.remote.ApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CourseRepository(
    private val apiService: ApiService
) {

    // =========================================================
    // GET ALL COURSES
    // =========================================================

    suspend fun getCourses(): Result<List<CourseResponse>> {

        return try {

            val response =
                apiService.getCourses()

            if (response.isSuccessful) {

                Result.success(
                    response.body() ?: emptyList()
                )

            } else {

                Result.failure(
                    Exception(
                        "Failed to load courses. HTTP ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(
                Exception(
                    e.message
                        ?: "Unable to connect to server."
                )
            )
        }
    }


    // =========================================================
    // CREATE COURSE
    // =========================================================

    suspend fun createCourse(
        request: CourseRequest,
        documentFiles: List<File>
    ): Result<CourseResponse> {

        return try {

            val response =
                apiService.createCourse(

                    courseName =
                        request.courseName.toPart(),

                    thumbnail =
                        request.thumbnail.toPart(),

                    duration =
                        request.duration.toPart(),

                    description =
                        request.description.toPart(),

                    price =
                        request.price
                            .toString()
                            .toPart(),

                    points =
                        Gson()
                            .toJson(request.points)
                            .toRequestBody(
                                "application/json"
                                    .toMediaType()
                            ),

                    documents =
                        documentFiles.map { file ->

                            createFilePart(file)
                        }
                )

            if (response.isSuccessful) {

                response.body()?.let {

                    Result.success(it)

                } ?: Result.failure(
                    Exception(
                        "Course created successfully, but server returned an empty response."
                    )
                )

            } else {

                Result.failure(
                    Exception(
                        getErrorMessage(
                            response.code()
                        )
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(
                Exception(
                    e.message
                        ?: "Unable to create course."
                )
            )
        }
    }


    // =========================================================
    // UPDATE COURSE
    // =========================================================

    suspend fun updateCourse(
        id: Long,
        request: CourseRequest,
        documentFiles: List<File>
    ): Result<CourseResponse> {

        return try {

            val response =
                apiService.updateCourse(

                    id = id,

                    courseName =
                        request.courseName.toPart(),

                    thumbnail =
                        request.thumbnail.toPart(),

                    duration =
                        request.duration.toPart(),

                    description =
                        request.description.toPart(),

                    price =
                        request.price
                            .toString()
                            .toPart(),

                    points =
                        Gson()
                            .toJson(request.points)
                            .toRequestBody(
                                "application/json"
                                    .toMediaType()
                            ),

                    documents =
                        documentFiles.map { file ->

                            createFilePart(file)
                        }
                )

            if (response.isSuccessful) {

                response.body()?.let {

                    Result.success(it)

                } ?: Result.failure(
                    Exception(
                        "Course updated successfully, but server returned an empty response."
                    )
                )

            } else {

                Result.failure(
                    Exception(
                        getErrorMessage(
                            response.code()
                        )
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(
                Exception(
                    e.message
                        ?: "Unable to update course."
                )
            )
        }
    }


    // =========================================================
    // DELETE COURSE
    // =========================================================

    suspend fun deleteCourse(
        id: Long
    ): Result<Unit> {

        return try {

            val response =
                apiService.deleteCourse(id)

            if (response.isSuccessful) {

                Result.success(Unit)

            } else {

                Result.failure(
                    Exception(
                        getErrorMessage(
                            response.code()
                        )
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(
                Exception(
                    e.message
                        ?: "Unable to delete course."
                )
            )
        }
    }


    // =========================================================
    // STRING -> MULTIPART TEXT
    // =========================================================

    private fun String.toPart(): RequestBody {

        return toRequestBody(
            "text/plain".toMediaType()
        )
    }


    // =========================================================
    // FILE -> MULTIPART FILE
    // =========================================================

    private fun createFilePart(
        file: File
    ): MultipartBody.Part {

        val requestFile =
            file.asRequestBody(
                "application/octet-stream"
                    .toMediaType()
            )

        return MultipartBody.Part.createFormData(
            "documents",
            file.name,
            requestFile
        )
    }


    // =========================================================
    // ERROR MESSAGE
    // =========================================================

    private fun getErrorMessage(
        code: Int
    ): String {

        return when (code) {

            400 ->
                "Invalid course details."

            401 ->
                "Session expired. Please login again."

            403 ->
                "You are not authorized to perform this action."

            404 ->
                "Course not found."

            409 ->
                "Course already exists."

            413 ->
                "File size is too large."

            500 ->
                "Server error. Please try again later."

            else ->
                "Request failed. HTTP $code"
        }
    }
}