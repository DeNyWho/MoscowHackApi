package com.example.data.response

@kotlinx.serialization.Serializable
data class EventResponse(
    val id: Int?,
    val description: String,
    val dateTime: String,
    val creator: Int,
    val hours: Int,
    val coins: Int
)
