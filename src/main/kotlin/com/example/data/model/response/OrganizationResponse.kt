package com.example.data.model.response

@kotlinx.serialization.Serializable
data class OrganizationResponse(
    val id: Int?,
    val userID: Int?,
    val name: String,
    val email: String,
    val description: String,
    val site: String
)
