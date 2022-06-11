package com.example.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
//    private val id: Column<Int> = integer("id")
    val email: Column<String> = varchar("email", 512)
    val name: Column<String> = varchar("name",512)
    val secondName: Column<String> = varchar("secondName",512)
    val type: Column<String> = varchar("type",512)
    val hashPassword: Column<String> = varchar("password",512)

    override val primaryKey: Table.PrimaryKey = PrimaryKey(email)
}