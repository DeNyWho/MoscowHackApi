package com.example.routes.volunteer

import com.example.repository.Repository
import com.example.routes.volunteer.VolunteerRoute.volunteer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getVolunteers(db: Repository) {
    get(volunteer) {
        try {
            val volunteerFromDb = db.getVolunteers()
            call.respond(volunteerFromDb)
        } catch (e: Throwable) {
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}