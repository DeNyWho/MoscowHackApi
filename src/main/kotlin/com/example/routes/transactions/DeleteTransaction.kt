package com.example.routes.transactions

import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.deleteTransaction(db: Repository) {
    delete<TransactionsRoute.TransactionDeleteRoute> { transactionDelete ->
        try {
            val result = db.deleteTransaction(transactionDelete.id)
            if (result) {
                call.respond("Successfully")
            }
        } catch (e: Throwable) {
            application.log.error("Failed to find event by id ${transactionDelete.id}", e)
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}