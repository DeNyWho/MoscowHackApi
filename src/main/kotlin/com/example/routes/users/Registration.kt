package com.example.routes.users

import com.example.authentication.JwtService
import com.example.authentication.ServerSession
import com.example.data.model.request.RegisterRequest
import com.example.data.model.response.AuthorizedUserResponse
import com.example.data.model.response.ErrorResponse
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.register(
    db: Repository,
    jwtService: JwtService,
    hashFunction: (String) -> String
) {
    post(UsersRoute.USER_REGISTER) {
        val signupParameters = call.receive<RegisterRequest>()
        val email = signupParameters.email
        val name = signupParameters.name
        val secondName = signupParameters.secondName
        val password = signupParameters.password
        val type = signupParameters.type

        if (!db.checkEmailAvailable(email)) {
            call.respond(message = ErrorResponse(errorCode = 400, message = "Already registered"), status = HttpStatusCode.BadRequest)
        }

        val hash = hashFunction(password)
        try {
            val newUser = db.createUser(email, hash, name, secondName, type)
            newUser?.id?.let {
                val authorizedUser = AuthorizedUserResponse(
                    id = it,
                    name = newUser.name,
                    secondName = newUser.secondName,
                    email = newUser.email,
                    type = newUser.type,
                    accessToken = jwtService.generateToken(newUser)
                )
                call.sessions.set(ServerSession(it))
                call.respond(authorizedUser)
            } ?:  call.respond(message = ErrorResponse(errorCode = 400, message = "Already registered"), status = HttpStatusCode.BadRequest)
        } catch (e: Throwable) {
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}