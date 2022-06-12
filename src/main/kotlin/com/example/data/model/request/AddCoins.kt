package com.example.data.model.request

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class AddCoinsRequest(
    val coins: Int,
    val option: String
): Principal
