package com.example.data.model

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class Prefs(
    val id: Int,
    val name: String,
): Principal

