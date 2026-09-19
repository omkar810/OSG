package com.example.omshivgoraksha.data.model.auth

data class SignupRequest(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: Long,
    val wpNumber: Long,
    val password: String
)