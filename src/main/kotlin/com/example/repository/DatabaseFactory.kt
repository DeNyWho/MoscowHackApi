package com.example.repository

import com.example.data.table.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(hikari())

        transaction {
            SchemaUtils.create(UserTable)
            SchemaUtils.create(VolunteersPrefs)
            SchemaUtils.create(OrganizationTable)
            SchemaUtils.create(EventsPrefs)
            SchemaUtils.create(SubmissionTable)
            SchemaUtils.create(TransactionTable)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://abul.db.elephantsql.com:5432/fyqembrw"
        config.username = "fyqembrw"
        config.password = "ke5IEKyPZU06qaD6IcW577naIjDl6tlU"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()

        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}