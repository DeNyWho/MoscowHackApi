package com.example.routes.events

import com.example.data.model.response.EventResponse
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.getEvent(db: Repository) {
    get<EventRoute.EventDetailsRoute> { eventDetails ->
        try {
            val eventDetailsFromDb = db.getEvent(eventDetails.id)
            eventDetailsFromDb.id.let {
                val event = EventResponse(
                    id = it,
                    name = eventDetailsFromDb.name,
                    description = eventDetailsFromDb.description,
                    dateTime = eventDetailsFromDb.dateTime,
                    creator = eventDetailsFromDb.creator,
                    hours = eventDetailsFromDb.hours,
                    coins = eventDetailsFromDb.coins,
                    city = eventDetailsFromDb.city,
                    place = eventDetailsFromDb.place,
                    methodEvent = eventDetailsFromDb.methodEvent,
                    roles = eventDetailsFromDb.roles,
                    age = eventDetailsFromDb.place,
                    skills = eventDetailsFromDb.skills
                )
                call.respond(event)
            }
        } catch (e: Throwable) {
            application.log.error("Failed to find event by id ${eventDetails.id}", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}