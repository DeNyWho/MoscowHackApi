package com.example.data.model.organization

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class Organization(
    var id: Int,
    var userId: Int,
    var email: String,
    var name: String,
    var description: String,
    var site: String
): Principal
