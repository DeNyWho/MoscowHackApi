package com.example.data.model.event

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class Event(
    val id: Int,
    val description: String,
    val dateTime: String,
    val creator: Int,
    val hours: Int,
    val coins: Int,
    val city: String,
    val place: String,
    val methodEvent: String,
    val roles: String,
    val age: String,
    val skills: String,
): Principal
