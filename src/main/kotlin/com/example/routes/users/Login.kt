package com.example.routes.users

import com.example.authentication.JwtService
import com.example.authentication.ServerSession
import com.example.data.model.request.LoginRequest
import com.example.data.model.response.AuthorizedUserResponse
import com.example.data.model.response.ErrorResponse
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.login(
    db: Repository,
    jwtService: JwtService,
    hashFunction: (String) -> String
) {
    post(UsersRoute.USER_LOGIN){
        val loginParameters = call.receive<LoginRequest>()
        val email = loginParameters.email
        val password = loginParameters.password

        val hash = hashFunction(password)
        try {
            val currentUser = db.getUserByEmail(email)
            currentUser?.id?.let {
                if (currentUser.password == hash) {
                    val authorizedUser = AuthorizedUserResponse(
                        id = it,
                        name = currentUser.name,
                        secondName = currentUser.secondName,
                        email = currentUser.email,
                        type = currentUser.type,
                        accessToken = jwtService.generateToken(currentUser)
                    )
                    call.sessions.set(ServerSession(it))
                    call.respond(authorizedUser)
                } else {
                    call.respond(message = ErrorResponse(errorCode = 403, message = "Wrong password"), status = HttpStatusCode.Forbidden)
                }
            } ?: call.respond(HttpStatusCode(404, "User not found"))
        } catch (e: Throwable) {
            application.log.error("Failed to sign in user", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}