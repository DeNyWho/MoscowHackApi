package com.example.data.model.submission

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class Submission(
    val id: Int,
    val eventID: Int,
    val userFrom: Int,
    val userTo: Int,
    val letter: String,
    val type: String,
    val status: String
)
: Principal
