package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.authentication.JwtService
import com.example.authentication.ServerSession
import com.example.authentication.hash
import com.example.repository.DatabaseFactory
import com.example.repository.RepositoryImpl
import com.example.routes.users.getUser
import com.example.routes.users.login
import com.example.routes.users.register
import com.example.routes.users.updateUser
import io.ktor.http.*
import io.ktor.server.sessions.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {
    install(Locations)
    install(Sessions) {
        cookie<ServerSession>("HACK_SESSION")
    }

    DatabaseFactory.init()
    val db = RepositoryImpl()
    val jwtService = JwtService()
    val hashFunction = { s: String -> hash(s) }

    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.verifier)
            realm = "Hack Server"
            validate {
                val payload = it.payload
                val claim = payload.getClaim("id")
                val claimString = claim.asInt()
                val user = db.getUser(claimString)
                user
            }
        }
    }

    routing {
        get("/") {
            return@get call.respond(HttpStatusCode.OK, "TEST")
        }

        // User routes
        login(db, jwtService, hashFunction)
        register(db, jwtService, hashFunction)
        getUser(db)
        updateUser(db, jwtService, hashFunction)

    }
}
