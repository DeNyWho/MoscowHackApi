package com.example.routes.volunteer

import io.ktor.server.locations.*

object VolunteerRoute {
    const val volunteer = "/volunteer"
    const val VOLUNTEER_INSERT = "$volunteer/insert"
    const val VOLUNTEER_BY_ID = "$volunteer/{id}"
    const val VOLUNTEER_UPDATE_COINS = "$volunteer/updateCoins/{id}"
    const val prefs = "/prefs"
    const val PREFS_BY_ID = "/prefs/{id}"

    @Location(VOLUNTEER_BY_ID)
    data class VolunteerDetailsRoute(val id: Int)

    @Location(PREFS_BY_ID)
    data class PrefsDetailsRoute(val id: Int)

    @Location(VOLUNTEER_UPDATE_COINS)
    data class VolunteerUpdateCoinsRoute(val id: Int)

}