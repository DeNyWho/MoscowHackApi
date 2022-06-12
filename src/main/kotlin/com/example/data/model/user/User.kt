package com.example.data.model.user

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class User(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val secondName: String,
    val type: String
): Principal

