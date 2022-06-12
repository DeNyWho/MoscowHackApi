package com.example.routes.users

import com.example.authentication.JwtService
import com.example.authentication.ServerSession
import com.example.data.model.User
import com.example.data.request.UpdateUserRequest
import com.example.data.response.AuthorizedUserResponse
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.updateUser(
    db: Repository,
    jwtService: JwtService,
    hashFunction: (String) -> String
) {
    authenticate("jwt") {
        put(UsersRoute.USER_UPDATE) {
            val user = call.authentication.principal<User>()
                ?: return@put call.respond(HttpStatusCode(406, "Authentication Error"))

            val updateUserParameters = call.receive<UpdateUserRequest>()
            val name = updateUserParameters.name
            val secondName = updateUserParameters.secondName
            val email = updateUserParameters.email
            val type = updateUserParameters.type
            val password = updateUserParameters.password
            val newPassword = updateUserParameters.newPassword

            if (password.isNullOrBlank() && !newPassword.isNullOrBlank()) {
                application.log.error("Failed to update user - no password")
                return@put call.respond(HttpStatusCode(413, "Missing password"))
            }

            val oldHash = password.let { hashFunction(it) }
            val newHash = newPassword?.let { hashFunction(it) }!!
            try {
                val userFromDb = db.getUser(user.id)
                userFromDb?.id?.let {
                    if (userFromDb.password != oldHash) {
                        application.log.error("Failed to update user - wrong password")
                        return@put call.respond(HttpStatusCode(414, "Wrong password"))
                    }

                    val updatedUser = db.updateUsers(user.id, email, newHash, name, secondName,type)
                    updatedUser?.id?.let {
                        val authorizedUser = AuthorizedUserResponse(
                            id = it,
                            name = updatedUser.name,
                            secondName = updatedUser.secondName,
                            email = updatedUser.email,
                            type = updatedUser.type,
                            accessToken = jwtService.generateToken(updatedUser)
                        )
                        call.sessions.set(ServerSession(it))
                        call.respond(authorizedUser)
                    } ?: call.respond(HttpStatusCode(415, "Failed to update user"))
                } ?: call.respond(HttpStatusCode(406, "Authentication Error"))
            } catch (e: Throwable) {
                application.log.error("Failed to update user", e)
                call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
            }
        }
    }
}