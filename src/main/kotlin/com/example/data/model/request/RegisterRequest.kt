package com.example.data.model.request

@kotlinx.serialization.Serializable
data class RegisterRequest(
    val email: String,
    val name: String,
    val secondName: String,
    val password: String,
    val type: String
)
