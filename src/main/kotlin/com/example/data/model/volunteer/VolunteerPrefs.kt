package com.example.data.model.volunteer

import com.example.data.model.Prefs

@kotlinx.serialization.Serializable
data class VolunteerPrefs(
    val id: Int,
    val prefs: Int
)