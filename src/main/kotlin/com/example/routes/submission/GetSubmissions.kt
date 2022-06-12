package com.example.routes.submission

import com.example.repository.Repository
import com.example.routes.submission.SubmissionRoute.submission
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSubmissions(db: Repository) {
    get(submission) {
        try {
            val submissionFromDb = db.getSubmissions()
            call.respond(submissionFromDb)
        } catch (e: Throwable) {
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}