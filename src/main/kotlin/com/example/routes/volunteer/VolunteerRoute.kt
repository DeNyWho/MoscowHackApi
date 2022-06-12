package com.example.routes.volunteer

import com.example.routes.organisation.OrganizationRoute
import io.ktor.server.locations.*

object VolunteerRoute {
    private const val volunteer = "/volunteer"
    const val VOLUNTEER_INSERT = "$volunteer/insert"
    const val VOLUNTEER_BY_ID = "$volunteer/{id}"

    @Location(VOLUNTEER_BY_ID)
    data class VolunteerDetailsRoute(val id: Int)
}