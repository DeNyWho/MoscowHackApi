package com.example.routes.submission

import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.deleteSubmission(db: Repository) {
    delete<SubmissionRoute.SubmissionDeleteRoute> { submissionDelete ->
        try {
            val result = db.deleteSubmission(submissionDelete.id)
            if (result) {
                call.respond("Successfully")
            }
        } catch (e: Throwable) {
            application.log.error("Failed to find event by id ${submissionDelete.id}", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}