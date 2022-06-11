package com.example.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val email: Column<String> = varchar("email", 512).uniqueIndex()
    val name: Column<String> = varchar("name", 200)
    val secondName: Column<String> = varchar("secondName",200)
    val type: Column<String> = varchar("type",100)
    val hashPassword: Column<String> = varchar("password",512)

    override val primaryKey: Table.PrimaryKey = PrimaryKey(id)
}