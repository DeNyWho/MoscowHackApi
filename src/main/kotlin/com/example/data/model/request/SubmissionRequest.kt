package com.example.data.model.request
@kotlinx.serialization.Serializable
data class SubmissionRequest(
    val eventID: Int,
    val userFrom: Int,
    val userTo: Int,
    val letter: String,
    val type: String,
    val status: String
)
