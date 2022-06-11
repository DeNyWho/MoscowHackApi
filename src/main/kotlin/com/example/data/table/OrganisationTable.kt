package com.example.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object OrganisationTable: Table() {
    val id: Column<Int> = integer("id").references(UserTable.id)
    val email: Column<String> = varchar("email", 512).uniqueIndex()
    val name: Column<String> = varchar("name", 200)
    val description: Column<String> = varchar("description",512)
    val phone: Column<String> = varchar("phone", 200)
//    val prefs: Column<List<Int>> = array

    override val primaryKey: Table.PrimaryKey = PrimaryKey(id)
}