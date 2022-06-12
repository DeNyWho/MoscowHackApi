package com.example.data.model.request

@kotlinx.serialization.Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
