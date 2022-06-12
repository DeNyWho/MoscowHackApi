package com.example.routes.events

import io.ktor.server.locations.*

object EventRoute {
    const val events = "/events"
    const val EVENT_INSERT = "$events/insert"
    const val EVENT_BY_ID = "$events/{id}"
    const val DELETE_EVENT = "$events/delete/{id}"
    const val EVENT_PREFS_BY_ID = "$events/{id}/prefs"

    @Location(EVENT_BY_ID)
    data class EventDetailsRoute(val id: Int)

    @Location(DELETE_EVENT)
    data class EventDeleteRoute(val id: Int)

    @Location(EVENT_PREFS_BY_ID)
    data class PrefsDetailsRoute(val id: Int)
}