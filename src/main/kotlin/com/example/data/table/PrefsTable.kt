package com.example.data.table

import com.example.data.table.OrganisationTable.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object PrefsTable: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name",200)

    override val primaryKey: Table.PrimaryKey = PrimaryKey(OrganisationTable.id)
}