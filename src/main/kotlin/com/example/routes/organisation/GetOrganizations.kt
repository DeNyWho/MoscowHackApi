package com.example.routes.organisation

import com.example.data.model.UserResponse
import com.example.data.response.ErrorResponse
import com.example.data.response.OrganizationResponse
import com.example.repository.Repository
import com.example.routes.users.UsersRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.getOrganizations(db: Repository) {
    get<OrganizationRoute.OrganizationDetailsRoute> { organizationDetails ->
        try {
            val organizationDetailsFromDb = db.getOrganizationById(organizationDetails.id)
            organizationDetailsFromDb?.id?.let {
                val organization = OrganizationResponse(
                    id = it,
                    userID = organizationDetailsFromDb.id,
                    name = organizationDetailsFromDb.name,
                    email = organizationDetailsFromDb.email,
                    description = organizationDetailsFromDb.description,
                    site = organizationDetailsFromDb.site
                )
                call.respond(organization)
            } ?: call.respond(
                message = ErrorResponse(errorCode = 400, message = "Data not exists"),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: Throwable) {
            application.log.error("Failed to find user by id ${organizationDetails.id}", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}