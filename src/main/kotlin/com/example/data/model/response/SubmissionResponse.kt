package com.example.data.model.response

@kotlinx.serialization.Serializable
data class SubmissionResponse(
    val id: Int?,
    val eventID: Int,
    val userFrom: Int,
    val userTo: Int,
    val letter: String,
    val type: String,
    val status: String
)
