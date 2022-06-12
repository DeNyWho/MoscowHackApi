package com.example.data.model.request

@kotlinx.serialization.Serializable
data class OrganizationRequest(
    val userID: Int,
    val email: String,
    val name: String,
    val description: String,
    val site: String
)
