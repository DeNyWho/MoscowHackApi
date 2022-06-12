package com.example.data.model.response

@kotlinx.serialization.Serializable
data class AuthorizedUserResponse(
    val id: Int?,
    val name: String,
    val secondName: String,
    val email: String,
    val type: String,
    val accessToken: String
)