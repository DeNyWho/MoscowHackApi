package com.example.routes.organisation

import com.example.repository.Repository
import com.example.routes.organisation.OrganizationRoute.organization
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getOrganizations(db: Repository) {
    get(organization) {
        try {
            val organizationsFromDb = db.getOrganizations()
            call.respond(organizationsFromDb)
        } catch (e: Throwable) {
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}