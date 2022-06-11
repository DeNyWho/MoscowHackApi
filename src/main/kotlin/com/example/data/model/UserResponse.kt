package com.example.data.model

@kotlinx.serialization.Serializable
data class UserResponse(
    val id: Int?,
    val name: String,
    val secondName: String,
    val email: String,
    val type: String
)