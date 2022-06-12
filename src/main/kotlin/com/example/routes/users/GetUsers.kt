package com.example.routes.users

import com.example.repository.Repository
import com.example.routes.users.UsersRoute.USERS
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUsers(db: Repository) {
    get(USERS) {
        try {
            val userFromDb = db.getUsers()
                call.respond(userFromDb)
        } catch (e: Throwable) {
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}