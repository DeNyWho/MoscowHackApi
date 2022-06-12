package com.example.data.model

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class Type(
    val type: String
):Principal
