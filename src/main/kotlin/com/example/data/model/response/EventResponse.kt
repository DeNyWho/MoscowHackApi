package com.example.data.model.response

@kotlinx.serialization.Serializable
data class EventResponse(
    val id: Int?,
    val description: String,
    val dateTime: String,
    val creator: Int,
    val hours: Int,
    val coins: Int,
    val city: String,
    val place: String
)
