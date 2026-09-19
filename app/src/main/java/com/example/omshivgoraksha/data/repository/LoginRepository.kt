package com.example.omshivgoraksha.data.repository

import com.example.omshivgoraksha.data.local.TokenManager
import com.example.omshivgoraksha.data.model.login.LoginRequest
import com.example.omshivgoraksha.data.remote.ApiService

class LoginRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {

    suspend fun login(
        userName: String,
        password: String
    ): Result<String> {

        return try {

            val request =
                LoginRequest(
                    userName = userName,
                    password = password
                )

            val response =
                apiService.login(request)

            if (response.isSuccessful) {

                val body = response.body()

                if (body == null ||
                    body.token.isBlank()
                ) {

                    return Result.failure(
                        Exception(
                            "Invalid response from server."
                        )
                    )
                }

                /*
                 * Save JWT
                 */

                tokenManager.saveToken(
                    body.token
                )

                Result.success(
                    "Login successful."
                )

            } else {

                when (response.code()) {

                    401 -> Result.failure(
                        Exception(
                            "Invalid username or password."
                        )
                    )

                    403 -> Result.failure(
                        Exception(
                            "You are not authorized to login."
                        )
                    )

                    400 -> Result.failure(
                        Exception(
                            "Invalid login request."
                        )
                    )

                    else -> Result.failure(
                        Exception(
                            "Login failed. Please try again."
                        )
                    )
                }
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

    suspend fun logout() {

        tokenManager.clearToken()
    }
}