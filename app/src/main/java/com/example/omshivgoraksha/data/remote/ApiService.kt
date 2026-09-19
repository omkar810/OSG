package com.example.omshivgoraksha.data.remote

import com.example.omshivgoraksha.data.model.auth.SignupRequest
import com.example.omshivgoraksha.data.model.course.request.CourseRequest
import com.example.omshivgoraksha.data.model.course.response.CourseResponse
import com.example.omshivgoraksha.data.model.login.LoginRequest
import com.example.omshivgoraksha.data.model.login.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

//    @POST("api/auth/login")
//    suspend fun login(
//        @Body request: LoginRequest
//    ): LoginResponse
//
//    @GET("api/user/profile")
//    suspend fun getProfile(): UserProfile

    @POST("api/auth/register")
    suspend fun signupUser(
        @Body request: SignupRequest
    ): Response<Any>

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("api/courses")
    suspend fun getCourses(): Response<List<CourseResponse>>

    @GET("api/courses/{id}")
    suspend fun getCourseById(
        @Path("id") id: Long
    ): Response<CourseResponse>

    @Multipart
    @POST("api/courses")
    suspend fun createCourse(

        @Part("courseName")
        courseName: RequestBody,

        @Part("thumbnail")
        thumbnail: RequestBody,

        @Part("duration")
        duration: RequestBody,

        @Part("description")
        description: RequestBody,

        @Part("price")
        price: RequestBody,

        @Part("points")
        points: RequestBody,

        @Part documents: List<MultipartBody.Part>

    ): Response<CourseResponse>

    @Multipart
    @PUT("api/courses/{id}")
    suspend fun updateCourse(

        @Path("id")
        id: Long,

        @Part("courseName")
        courseName: RequestBody,

        @Part("thumbnail")
        thumbnail: RequestBody,

        @Part("duration")
        duration: RequestBody,

        @Part("description")
        description: RequestBody,

        @Part("price")
        price: RequestBody,

        @Part("points")
        points: RequestBody,

        @Part documents: List<MultipartBody.Part>

    ): Response<CourseResponse>


    @PUT("api/courses/{id}")
    suspend fun updateCourse(
        @Path("id") id: Long,
        @Body request: CourseRequest
    ): Response<CourseResponse>

    @DELETE("api/courses/{id}")
    suspend fun deleteCourse(
        @Path("id") id: Long
    ): Response<Unit>
}