package com.example.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object OrganizationTable: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val userID: Column<Int> = integer("userID")
    val email: Column<String> = varchar("email", 512)
    val name: Column<String> = varchar("name", 200)
    val description: Column<String> = varchar("description",200)
    val site: Column<String> = varchar("site",512)

    override val primaryKey: Table.PrimaryKey = PrimaryKey(id)
}