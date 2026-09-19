package com.example.omshivgoraksha.data.remote

import com.example.omshivgoraksha.data.local.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {

        val originalRequest = chain.request()

        val path = originalRequest.url.encodedPath

        /*
         * APIs which should NOT receive JWT
         */

        val isPublicApi =
            path.endsWith("/login") ||
                    path.endsWith("/register") ||
                    path.endsWith("/forgotPassword")

        if (isPublicApi) {

            return chain.proceed(
                originalRequest
            )
        }

        /*
         * Get JWT token
         */

        val token = runBlocking {

            tokenManager.token.first()
        }

        /*
         * If token exists, add Authorization header
         */

        val request = if (!token.isNullOrBlank()) {

            originalRequest.newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer $token"
                )
                .build()

        } else {
            originalRequest
        }

        return chain.proceed(request)
    }
}