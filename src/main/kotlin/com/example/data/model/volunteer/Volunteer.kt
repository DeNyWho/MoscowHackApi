package com.example.data.model.volunteer

import io.ktor.server.auth.*
@kotlinx.serialization.Serializable

data class Volunteer(
    val id: Int,
    val userId: Int,
    val description: String,
    val phone: String,
    val hours: Int,
    val coins: Int
): Principal


