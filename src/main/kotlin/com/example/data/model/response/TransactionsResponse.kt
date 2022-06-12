package com.example.data.model.response

import io.ktor.server.auth.*
@kotlinx.serialization.Serializable
data class TransactionsResponse (
    val id: Int,
    val coins: Int,
    val hours: Int,
    val description: String,
    val from: Int,
    val to: Int,
    val event: Int
): Principal
