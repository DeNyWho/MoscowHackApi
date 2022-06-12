package com.example.routes.volunteer

import com.example.data.request.VolunteerRequest
import com.example.data.response.Status
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertVolunteer(
    db: Repository
) {
    post(VolunteerRoute.VOLUNTEER_INSERT){
        try {
            val parameters = call.receive<VolunteerRequest>()
            val id = parameters.userID
            val prefs = parameters.prefs
            val description = parameters.description
            val coins = parameters.coins
            val hours = parameters.hours
            val phone = parameters.phone

            db.insertVolunteer(
                userId = id,
                description = description,
                phone = phone,
                hours = hours,
                coins = coins,
                prefs = prefs
            )
            call.respond(message = Status(status = 200, message = "Successfully"), status = HttpStatusCode.OK)
        } catch (e: Exception){
            call.respond(message = Status(status = 400, message = "${e.message}"), status = HttpStatusCode.BadRequest)
        }
    }
}