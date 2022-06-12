package com.example.data.response
@kotlinx.serialization.Serializable
data class Status(
    val status: Int,
    val message:String
)