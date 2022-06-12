package com.example.data.model.prefs

import io.ktor.server.auth.*
import org.jetbrains.exposed.dao.id.EntityID

@kotlinx.serialization.Serializable
data class PrefsUser(
    val pref: Int
): Principal