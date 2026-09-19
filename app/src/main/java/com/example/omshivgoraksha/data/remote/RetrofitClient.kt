//package com.example.omshivgoraksha.data.remote
//
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//object RetrofitClient {
//
//    // ---------------------------------------------------------
//    // Base URL
//    // ---------------------------------------------------------
//    //
//    // IMPORTANT:
//    // Change this according to your Spring Boot server.
//    //
//    // Local computer:
//    // http://192.168.1.10:8080/
//    //
//    // Production:
//    // https://your-domain.com/
//    //
//    // Do NOT use localhost when testing from Android phone.
//    // ---------------------------------------------------------
//
////    private const val BASE_URL = "http://192.168.1.10:8080/"
//private const val BASE_URL = "http://31.220.76.155:2026/"
//
//    // ---------------------------------------------------------
//    // Logging
//    // ---------------------------------------------------------
//
//    private val loggingInterceptor =
//        HttpLoggingInterceptor().apply {
//
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//
//    // ---------------------------------------------------------
//    // OkHttp Client
//    // ---------------------------------------------------------
//
//    private val okHttpClient =
//        OkHttpClient.Builder()
//
//            .addInterceptor(loggingInterceptor)
//
//            .connectTimeout(
//                30,
//                TimeUnit.SECONDS
//            )
//
//            .readTimeout(
//                30,
//                TimeUnit.SECONDS
//            )
//
//            .writeTimeout(
//                30,
//                TimeUnit.SECONDS
//            )
//
//            .build()
//
//
//    // ---------------------------------------------------------
//    // Retrofit
//    // ---------------------------------------------------------
//
//    private val retrofit: Retrofit =
//        Retrofit.Builder()
//
//            .baseUrl(BASE_URL)
//
//            .client(okHttpClient)
//
//            .addConverterFactory(
//                GsonConverterFactory.create()
//            )
//
//            .build()
//
//
//    // ---------------------------------------------------------
//    // API Service
//    // ---------------------------------------------------------
//
//    val apiService: ApiService =
//        retrofit.create(
//            ApiService::class.java
//        )
//}

package com.example.omshivgoraksha.data.remote

import android.content.Context
import com.example.omshivgoraksha.data.local.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL =
        "http://31.220.76.155:2026/"

    private lateinit var tokenManager: TokenManager

    private lateinit var retrofit: Retrofit

    lateinit var apiService: ApiService
        private set

    fun initialize(context: Context) {

        tokenManager =
            TokenManager(context.applicationContext)

        val loggingInterceptor =
            HttpLoggingInterceptor().apply {

                level =
                    HttpLoggingInterceptor.Level.BODY
            }

        val authInterceptor =
            AuthInterceptor(tokenManager)

        val okHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(
                    30,
                    TimeUnit.SECONDS
                )
                .readTimeout(
                    30,
                    TimeUnit.SECONDS
                )
                .writeTimeout(
                    30,
                    TimeUnit.SECONDS
                )
                .build()

        retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .build()

        apiService =
            retrofit.create(
                ApiService::class.java
            )
    }
}
