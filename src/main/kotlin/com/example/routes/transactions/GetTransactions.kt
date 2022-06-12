package com.example.routes.transactions

import com.example.repository.Repository
import com.example.routes.transactions.TransactionsRoute.transaction
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getTransactions(db: Repository) {
    get(transaction) {
        try {
            val transactionFromDb = db.getTransaction()
            call.respond(transactionFromDb)
        } catch (e: Throwable) {
            call.respond(HttpStatusCode(400, "Failed to execute request (exception ${e.localizedMessage})"))
        }
    }
}