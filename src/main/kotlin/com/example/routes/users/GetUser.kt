package com.example.routes.users

import com.example.data.model.UserResponse
import com.example.data.response.ErrorResponse
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.getUser(db: Repository) {
    get<UsersRoute.UserDetailsRoute> { userDetails ->
        try {
            val userFromDb = db.getUser(userDetails.id)
            userFromDb?.id?.let {
                val user = UserResponse(
                    id = it,
                    name = userFromDb.name,
                    secondName = userFromDb.secondName,
                    email = userFromDb.email,
                    type = userFromDb.type
                )
                call.respond(user)
            } ?: call.respond(HttpStatusCode.NoContent, ErrorResponse(406, "Authentication Error"))
        } catch (e: Throwable) {
            application.log.error("Failed to find user by id ${userDetails.id}", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}