package com.example.routes.submission

import com.example.data.model.response.ErrorResponse
import com.example.data.model.response.SubmissionResponse
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.getSubmissionById(db: Repository) {
    get<SubmissionRoute.SubmissionDetailsRoute> {submissionDetails ->
        try {
            val submissionDetailsFromDb = db.getSubmissionById(submissionDetails.id)
            submissionDetailsFromDb?.id?.let {
                val submission = SubmissionResponse(
                    id = it,
                    eventID = submissionDetailsFromDb.eventID,
                    userFrom = submissionDetailsFromDb.userFrom,
                    userTo = submissionDetailsFromDb.userTo,
                    letter = submissionDetailsFromDb.letter,
                    type = submissionDetailsFromDb.type,
                    status = submissionDetailsFromDb.status
                )
                call.respond(submission)
            } ?: call.respond(
                message = ErrorResponse(errorCode = 400, message = "Data not exists"),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: Throwable) {
            application.log.error("Failed to find submission by id ${submissionDetails.id}", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}