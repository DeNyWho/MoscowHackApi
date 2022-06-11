package com.example.routes.prefs

import com.example.data.model.Prefs
import com.example.data.model.UserResponse
import com.example.data.response.ErrorResponse
import com.example.data.table.PrefsTable
import com.example.repository.DatabaseFactory
import com.example.repository.Repository
import com.example.routes.users.UsersRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert

fun Route.setPrefs() {
    post("/prefs") {
        try {
            DatabaseFactory.dbQuery {
                PrefsTable.insert {
                    it[id] = 1
                    it[name] = "Social"
                }
                PrefsTable.insert {
                    it[id] = 2
                    it[name] = "Ecology"
                }
                PrefsTable.insert {
                    it[id] = 3
                    it[name] = "Culture"
                }
                PrefsTable.insert {
                    it[id] = 4
                    it[name] = "Media"
                }
                PrefsTable.insert {
                    it[id] = 5
                    it[name] = "Event"
                }
                PrefsTable.insert {
                    it[id] = 6
                    it[name] = "Sport"
                }
                PrefsTable.insert {
                    it[id] = 7
                    it[name] = "Patriotic"
                }
                PrefsTable.insert {
                    it[id] = 8
                    it[name] = "Animal"
                }
                PrefsTable.insert {
                    it[id] = 9
                    it[name] = "Corporate"
                }
                PrefsTable.insert {
                    it[id] = 10
                    it[name] = "Medicine"
                }
                PrefsTable.insert {
                    it[id] = 11
                    it[name] = "Sociality"
                }
            }
                call.respond("Data successfully added")
            } catch (e: Throwable) {
            application.log.error("Failed to add prefs")
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }

}