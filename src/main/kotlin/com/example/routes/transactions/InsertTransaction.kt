package com.example.routes.transactions

import com.example.data.model.request.TransactionsRequest
import com.example.data.model.response.ErrorResponse
import com.example.data.model.response.TransactionsResponse
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertTransactions(
    db: Repository
) {
    post(TransactionsRoute.TRANSACTION_INSERT) {
        val parameters = call.receive<TransactionsRequest>()
        val coins = parameters.coins
        val hours = parameters.hours
        val description = parameters.description
        val from = parameters.from
        val to = parameters.to
        val event = parameters.event


        try {
            val newTransaction = db.insertTransaction(
                coins = coins,
                hours = hours,
                description = description,
                from = from,
                to = to,
                event = event
            )
            newTransaction?.id?.let {
                val transaction = TransactionsResponse(
                    id = it,
                    coins = newTransaction.coins,
                    hours = newTransaction.hours,
                    description = newTransaction.description,
                    from = newTransaction.from,
                    to = newTransaction.to,
                    event = newTransaction.event
                )
                call.respond(transaction)
            } ?: call.respond(
                message = ErrorResponse(errorCode = 400, message = "Already added"),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: Throwable) {
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}