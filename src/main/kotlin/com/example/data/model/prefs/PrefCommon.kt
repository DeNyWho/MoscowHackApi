package com.example.data.model.prefs

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class PrefCommon(
    val name: String
): Principal
