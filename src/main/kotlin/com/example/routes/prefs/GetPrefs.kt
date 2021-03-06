package com.example.routes.prefs

import com.example.repository.Repository
import com.example.routes.volunteer.VolunteerRoute
import com.example.routes.volunteer.VolunteerRoute.prefs
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPrefs(db: Repository){
    get(prefs) {
        try {
            val prefs = db.getPrefs()
            application.log.error("$prefs")
            call.respond(prefs)
        } catch (e: Throwable) {
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}