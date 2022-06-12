package com.example.data.model.request

import io.ktor.server.auth.*
@kotlinx.serialization.Serializable
class TransactionsRequest (
    val coins: Int,
    val hours: Int,
    val description: String,
    val from: Int,
    val to: Int,
    val event: Int
): Principal