package com.example.data.model.common

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class Status(
    val status: String
): Principal