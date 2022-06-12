package com.example.routes.prefs

import com.example.data.table.Prefs
import com.example.repository.DatabaseFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert

fun Route.setPrefs() {
    post("/prefs/insert") {
        try {
            DatabaseFactory.dbQuery {
                Prefs.insert {
                    it[name] = "Social"
                }
                Prefs.insert {
                    it[name] = "Ecology"
                }
                Prefs.insert {
                    it[name] = "Culture"
                }
                Prefs.insert {
                    it[name] = "Media"
                }
                Prefs.insert {
                    it[name] = "Event"
                }
                Prefs.insert {
                    it[name] = "Sport"
                }
                Prefs.insert {
                    it[name] = "Patriotic"
                }
                Prefs.insert {
                    it[name] = "Animal"
                }
                Prefs.insert {
                    it[name] = "Corporate"
                }
                Prefs.insert {
                    it[name] = "Medicine"
                }
                Prefs.insert {
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