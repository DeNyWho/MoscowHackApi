package com.example.data.model.response
@kotlinx.serialization.Serializable
data class Status(
    val status: Int,
    val message:String
)