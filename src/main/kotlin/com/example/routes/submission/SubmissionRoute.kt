package com.example.routes.submission

import io.ktor.server.locations.*

object SubmissionRoute {
    const val submission = "/submission"
    const val SUBMISSION_INSERT = "$submission/insert"
    const val SUBMISSION_BY_ID = "$submission/{id}"
    const val SUBMISSION_DELETE= "$submission/delete/{id}"
    const val SUBMISSION_TYPE = "$submission/type/{id}"
    const val SUBMISSION_STATUS = "$submission/status/{id}"

    @Location(SUBMISSION_BY_ID)
    data class SubmissionDetailsRoute(val id: Int)

    @Location(SUBMISSION_DELETE)
    data class SubmissionDeleteRoute(val id: Int)

    @Location(SUBMISSION_TYPE)
    data class SubmissionTypeRoute(val id: Int)

    @Location(SUBMISSION_STATUS)
    data class SubmissionStatusRoute(val id: Int)
}