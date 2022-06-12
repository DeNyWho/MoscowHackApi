package com.example.routes.transactions

import com.example.data.model.response.ErrorResponse
import com.example.data.model.response.TransactionsResponse
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.getTransactionById(db: Repository) {
    get<TransactionsRoute.TransactionDetailsRoute> { transactionDetails ->
        try {
            val transactionDetailsFromDb = db.getTransactionById(transactionDetails.id)
            transactionDetailsFromDb?.id?.let {
                val transactions = TransactionsResponse(
                    id = it,
                    coins = transactionDetailsFromDb.coins,
                    hours = transactionDetailsFromDb.hours,
                    description = transactionDetailsFromDb.description,
                    from = transactionDetailsFromDb.from,
                    to = transactionDetailsFromDb.to,
                    event = transactionDetailsFromDb.event
                )
                call.respond(transactions)
            } ?: call.respond(
                message = ErrorResponse(errorCode = 400, message = "Data not exists"),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: Throwable) {
            application.log.error("Failed to find transactions by id ${transactionDetails.id}", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}