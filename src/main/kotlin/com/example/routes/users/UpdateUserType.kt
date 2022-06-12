package com.example.routes.users

import com.example.data.model.Type
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.updateUserType(db: Repository) {
    post<UsersRoute.UserUpdateTypeRoute> { type ->
        try {
            val param = call.receive<Type>()

            val response = db.updateUserType(type.id, param.type)
            if (response) {
                call.respond("Successfully")
            }
        } catch (e: Throwable) {
            application.log.error("Failed to find user by id ${type.id}", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}