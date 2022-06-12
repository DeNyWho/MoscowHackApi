package com.example.data.model.response

@kotlinx.serialization.Serializable
data class ErrorResponse(
    val errorCode: Int,
    val message: String
)
