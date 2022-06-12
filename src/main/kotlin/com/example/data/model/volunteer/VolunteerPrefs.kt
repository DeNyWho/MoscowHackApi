package com.example.data.model.volunteer

@kotlinx.serialization.Serializable
data class VolunteerPrefs(
    val id: Int,
    val prefs: Int
)