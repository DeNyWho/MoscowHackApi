package com.example.plugins

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.repository.DatabaseFactory
import com.example.repository.RepositoryImpl
import com.example.routes.organisation.getOrganization
import com.example.routes.organisation.insertOrganization
import com.example.routes.prefs.setPrefs
import com.example.routes.users.*
import com.example.routes.volunteer.insertVolunteer
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.locations.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    install(Locations)

    DatabaseFactory.init()
    val db = RepositoryImpl()
    val jwtService = JwtService()
    val hashFunction = { s: String -> hash(s) }

    routing {
        get("/") {
            return@get call.respond(HttpStatusCode.OK, "TEST")
        }

        // User routes
        login(db, jwtService, hashFunction)
        register(db, jwtService, hashFunction)
        getUser(db)
        updateUser(db, jwtService, hashFunction)
        getUsers(db)

        // Prefs Routes
        setPrefs()
        insertVolunteer(db)

        //Organization
        insertOrganization(db)
        getOrganization(db)
    }

}
