package com.example.data.response

@kotlinx.serialization.Serializable
data class OrganizationResponse(
    val id: Int?,
    val userID: Int?,
    val name: String,
    val email: String,
    val description: String,
    val site: String
)
