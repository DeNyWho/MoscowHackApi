package com.example.routes.submission

import com.example.data.model.request.SubmissionRequest
import com.example.data.model.response.ErrorResponse
import com.example.data.model.response.SubmissionResponse
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertSubmission(
    db: Repository
) {
    post(SubmissionRoute.SUBMISSION_INSERT) {
        val parameters = call.receive<SubmissionRequest>()
        val id = parameters.eventID
        val userFrom = parameters.userFrom
        val userTo = parameters.userTo
        val letter = parameters.letter
        val type = parameters.type
        val status = parameters.status

        try {
            val newSubmission = db.insertSubmission(
                eventID = id,
                userFrom = userFrom,
                userTo = userTo,
                letter = letter,
                type = type,
                status = status
            )
            newSubmission?.id?.let {
                val submission = SubmissionResponse(
                    id = it,
                    eventID = newSubmission.eventID,
                    userFrom = newSubmission.userFrom,
                    userTo = newSubmission.userTo,
                    letter = newSubmission.letter,
                    type = newSubmission.type,
                    status = newSubmission.status
                )
                call.respond(submission)
            } ?: call.respond(
                message = ErrorResponse(errorCode = 400, message = "Already added"),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: Throwable) {
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}