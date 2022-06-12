package com.example.data.model.request

@kotlinx.serialization.Serializable
data class EventRequest(
    val description: String,
    val dateTime: String,
    val creator: Int,
    val hours: Int,
    val coins: Int,
    val prefs: List<Int>,
    val city: String,
    val place: String
)
