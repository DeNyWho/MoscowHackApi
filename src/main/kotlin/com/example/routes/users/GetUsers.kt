package com.example.routes.users

import com.example.data.model.UserResponse
import com.example.data.response.ErrorResponse
import com.example.repository.Repository
import com.example.routes.users.UsersRoute.USERS
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.getUsers(db: Repository) {
    get(USERS) { userDetails ->
        try {
            val userFromDb = db.getUsers()
                call.respond(userFromDb)
        } catch (e: Throwable) {
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}