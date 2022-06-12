package com.example.plugins

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.repository.DatabaseFactory
import com.example.repository.RepositoryImpl
import com.example.routes.events.deleteEvent
import com.example.routes.events.getEvent
import com.example.routes.events.getEvents
import com.example.routes.events.insertEvent
import com.example.routes.organisation.getOrganization
import com.example.routes.organisation.getOrganizations
import com.example.routes.organisation.insertOrganization
import com.example.routes.prefs.getEventPref
import com.example.routes.prefs.getPrefs
import com.example.routes.prefs.getVolunteerPrefs
import com.example.routes.prefs.setPrefs
import com.example.routes.users.*
import com.example.routes.volunteer.insertVolunteer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
        updateUserType(db)

        // Prefs Routes
        setPrefs()
        insertVolunteer(db)
        getVolunteerPrefs(db)
        getPrefs(db)

        //Organization
        insertOrganization(db)
        getOrganization(db)
        getOrganizations(db)

        //Events
        insertEvent(db)
        getEvents(db)
        getEventPref(db)
        getEvent(db)
        deleteEvent(db)

    }

}
