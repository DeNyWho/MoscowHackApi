package com.example.data.model.request

@kotlinx.serialization.Serializable
data class VolunteerRequest(
    val userID: Int,
    val description: String,
    val phone: String,
    val prefs: List<Int>,
    val hours: Int,
    val coins: Int
)
