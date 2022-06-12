package com.example.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object SubmissionTable: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val eventID: Column<Int> = integer("eventID")
    val userFrom: Column<Int> = integer("userFrom")
    val userTo: Column<Int> = integer("userTo")
    val letter: Column<String> = varchar("letter", 512)
    val type: Column<String> = varchar("type",10)
    val status: Column<String> = varchar("status", 20)
    override val primaryKey: Table.PrimaryKey = PrimaryKey(id)
}