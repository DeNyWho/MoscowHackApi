package com.example.routes.transactions

import io.ktor.server.locations.*

object TransactionsRoute {
    const val transaction = "/transaction"
    const val TRANSACTION_INSERT = "$transaction/insert"
    const val TRANSACTION_BY_ID = "$transaction/{id}"
    const val TRANSACTION_DELETE= "$transaction/delete/{id}"

    @Location(TRANSACTION_BY_ID)
    data class TransactionDetailsRoute(val id: Int)

    @Location(TRANSACTION_DELETE)
    data class TransactionDeleteRoute(val id: Int)
}