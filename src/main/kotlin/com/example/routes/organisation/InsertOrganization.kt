package com.example.routes.organisation

import com.example.authentication.ServerSession
import com.example.data.request.OrganizationRequest
import com.example.data.request.VolunteerRequest
import com.example.data.response.AuthorizedUserResponse
import com.example.data.response.ErrorResponse
import com.example.data.response.OrganizationResponse
import com.example.data.response.Status
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.insertOrganization(
    db: Repository
) {
    post(OrganizationRoute.ORGANIZATION_INSERT) {
        val parameters = call.receive<OrganizationRequest>()
        val id = parameters.userID
        val email = parameters.email
        val name = parameters.name
        val description = parameters.description
        val site = parameters.site

        try {
            val newOrganization = db.insertOrganization(
                userId = id,
                email = email,
                name = name,
                description = description,
                site = site
            )
            newOrganization?.id?.let {
                val organization = OrganizationResponse(
                    id = it,
                    userID = newOrganization.id,
                    name = newOrganization.name,
                    email = newOrganization.email,
                    description = newOrganization.description,
                    site = newOrganization.site
                )
                call.respond(organization)
            } ?: call.respond(
                message = ErrorResponse(errorCode = 400, message = "Already added"),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: Throwable) {
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}