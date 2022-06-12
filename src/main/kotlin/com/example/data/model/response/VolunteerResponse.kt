package com.example.data.model.response

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class VolunteerResponse (
    val id: Int,
    val userID: Int,
    val description: String,
    val phone: String,
    val hours: Int,
    val coins: Int
): Principal

