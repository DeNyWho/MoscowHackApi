package com.example.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object TransactionTable: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val coins: Column<Int> = integer("coins")
    val hours: Column<Int> = integer("hours")
    val description: Column<String> = varchar("description",200)
    val from: Column<Int> = integer("from")
    val to: Column<Int> = integer("to")
    val event: Column<Int> = integer("event")

    override val primaryKey: Table.PrimaryKey = PrimaryKey(id)
}