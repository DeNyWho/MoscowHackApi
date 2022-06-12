package com.example.routes.volunteer

import com.example.data.model.request.AddCoinsRequest
import com.example.data.model.response.Status
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.updateCoinsToVolunteer(
    db: Repository
) {
    post<VolunteerRoute.VolunteerUpdateCoinsRoute>{ volunteer ->
        try {
            val parameters = call.receive<AddCoinsRequest>()
            val coins = parameters.coins
            val option = parameters.option

            db.updateCoins(
                volunteerID = volunteer.id,
                coins = coins,
                option = option
            )
            call.respond(message = Status(status = 200, message = "Successfully"), status = HttpStatusCode.OK)
        } catch (e: Exception){
            call.respond(message = Status(status = 400, message = "${e.message}"), status = HttpStatusCode.BadRequest)
        }
    }
}