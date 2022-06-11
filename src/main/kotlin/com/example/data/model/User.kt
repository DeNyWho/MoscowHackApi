package com.example.data.model

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class User(
    val email: String,
    val password: String,
    val name: String,
    val secondName: String,
    val type: String
): Principal

