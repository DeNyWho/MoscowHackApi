package com.example.data.model

@kotlinx.serialization.Serializable
data class RegisterRequest(
    val email: String,
    val name: String,
    val secondName: String,
    val password: String,
    val type: String
)
