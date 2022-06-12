package com.example.routes.volunteer

import com.example.data.model.response.ErrorResponse
import com.example.data.model.volunteer.Volunteer
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.getVolunteerById(db: Repository) {
    get<VolunteerRoute.VolunteerDetailsRoute> { volunteerDetails ->
        try {
            val volunteerDetailsFromDb = db.getVolunteerById(volunteerDetails.id)
            volunteerDetailsFromDb?.id?.let {
                val volunteer = Volunteer(
                    id = it,
                    userId = volunteerDetailsFromDb.userId,
                    description = volunteerDetailsFromDb.description,
                    phone = volunteerDetailsFromDb.phone,
                    hours = volunteerDetailsFromDb.hours,
                    coins = volunteerDetailsFromDb.coins,
                )
                call.respond(volunteer)
            } ?: call.respond(
                message = ErrorResponse(errorCode = 400, message = "Data not exists"),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: Throwable) {
            application.log.error("Failed to find volunteer by id ${volunteerDetails.id}", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}