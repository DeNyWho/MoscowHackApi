package com.example.data.response

@kotlinx.serialization.Serializable
data class ErrorResponse(
    val errorCode: Int,
    val message: String
)
