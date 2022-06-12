package com.example.routes.organisation

import com.example.routes.users.UsersRoute
import io.ktor.server.locations.*

object OrganizationRoute {
    const val organization = "/organization"
    const val ORGANIZATION_INSERT = "$organization/insert"
    const val ORGANIZATION_BY_ID = "$organization/{id}"

    @Location(ORGANIZATION_BY_ID)
    data class OrganizationDetailsRoute(val id: Int)
}