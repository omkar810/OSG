package com.example.omshivgoraksha.data.repository

import com.example.omshivgoraksha.data.model.auth.SignupRequest
import com.example.omshivgoraksha.data.remote.ApiService

class SignupRepository(
    private val apiService: ApiService
) {

    suspend fun signupUser(
        request: SignupRequest
    ): Result<String> {

        return try {

            val response = apiService.signupUser(request)

            if (response.isSuccessful) {

                Result.success(
                    "Account created successfully"
                )

            } else {

                val errorMessage = when (response.code()) {

                    400 -> "Invalid signup details."

                    409 -> "Username or email already exists."

                    500 -> "Server error. Please try again later."

                    else -> "Signup failed. Please try again."
                }

                Result.failure(
                    Exception(errorMessage)
                )
            }

        } catch (e: Exception) {

            Result.failure(
                Exception(
                    e.message ?: "Unable to connect to server."
                )
            )
        }
    }
}