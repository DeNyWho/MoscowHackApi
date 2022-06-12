package com.example.data.request

@kotlinx.serialization.Serializable
data class UpdateUserRequest(
    val name: String,
    val secondName: String,
    val email: String,
    val type: String,
    val password: String,
    val newPassword: String?
)