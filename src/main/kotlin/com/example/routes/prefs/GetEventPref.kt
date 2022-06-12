package com.example.routes.prefs

import com.example.repository.Repository
import com.example.routes.events.EventRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.getEventPref(db: Repository){
    get<EventRoute.PrefsDetailsRoute> { prefsDetails ->
        try {
            val prefs = db.getEventPref(prefsDetails.id)
            application.log.error("$prefs")
            call.respond(prefs)
        } catch (e: Throwable) {
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}