package com.example.routes.events

import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.deleteEvent(db: Repository) {
    delete<EventRoute.EventDeleteRoute> { eventDelete ->
        try {
            val result = db.deleteEvent(eventDelete.id)
            if (result) {
                call.respond("Successfully")
            }
        } catch (e: Throwable) {
            application.log.error("Failed to find event by id ${eventDelete.id}", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}