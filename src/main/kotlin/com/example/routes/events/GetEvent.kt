package com.example.routes.events

import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getEvents(db: Repository) {
    get(EventRoute.events) {
        try {
            val eventsFromDb = db.getEvents()
            call.respond(eventsFromDb)
        } catch (e: Throwable) {
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}