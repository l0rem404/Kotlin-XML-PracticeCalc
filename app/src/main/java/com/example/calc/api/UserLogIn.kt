package com.example.calc.api

data class UserLogIn(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val user: User,
    val token: String,
    val tokenType: String
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val email_verified_at: String?,
    val created_at: String,
    val updated_at: String
)

data class LogoutResponse(
    val message: String
)


