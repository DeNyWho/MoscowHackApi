package com.example.routes.events

import com.example.data.model.request.EventRequest
import com.example.data.model.response.ErrorResponse
import com.example.data.model.response.EventResponse
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertEvent(
    db: Repository
) {
    post(EventRoute.EVENT_INSERT) {
        val parameters = call.receive<EventRequest>()

        val name = parameters.name
        val description = parameters.description
        val dateTime = parameters.dateTime
        val creator = parameters.creator
        val hours = parameters.hours
        val coins = parameters.coins
        val prefs = parameters.prefs
        val city = parameters.city
        val place = parameters.place
        val methodEvent = parameters.methodEvent
        val roles = parameters.roles
        val age = parameters.age
        val skills = parameters.skills

        try {
            val newEvent = db.insertEvent(
                name = name,
                description = description,
                dateTime = dateTime,
                creator = creator,
                hours = hours,
                coins = coins,
                city = city,
                place = place,
                methodEvent = methodEvent,
                roles = roles,
                age = age,
                skills = skills
            )
            newEvent?.id?.let {
                val event = EventResponse(
                    id = it,
                    name = name,
                    description = description,
                    dateTime = dateTime,
                    creator = creator,
                    hours = hours,
                    coins = coins,
                    city = city,
                    place = place,
                    methodEvent = methodEvent,
                    roles = roles,
                    age = age,
                    skills = skills
                )
                call.respond(event)
            }
            if (newEvent == null){
                call.respond(
                    message = ErrorResponse(errorCode = 400, message = "Already added"),
                    status = HttpStatusCode.BadRequest
                )
            }
            else {
                db.insertEventPrefs(
                    eventId = newEvent.id,
                    prefs = prefs
                )
                call.respond(newEvent)
            }
        } catch (e: Throwable) {
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}