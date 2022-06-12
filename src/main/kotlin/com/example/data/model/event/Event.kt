package com.example.data.model.event

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class Event(
    val id: Int,
    val description: String,
    val dateTime: String,
    val creator: Int,
    val hours: Int,
    val coins: Int
): Principal
