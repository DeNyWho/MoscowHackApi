package com.example.data.model.transactions

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class Transactions(
    val id: Int,
    val coins: Int,
    val hours: Int,
    val description: String,
    val from: Int,
    val to: Int,
    val event: Int
): Principal

