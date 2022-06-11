package com.example.repository

import com.example.data.model.User
import com.example.data.table.UserTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

class RepositoryImpl: Repository {
    override suspend fun checkEmailAvailable(email: String): Boolean = dbQuery {
        UserTable.select { UserTable.email eq email }.map { rowToUser(it) }.isNullOrEmpty()
    }

    override suspend fun createUser(
        email: String,
        passwordHash: String,
        name: String,
        secondName: String,
        type: String
    ): User? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = UserTable.insert { user ->
                user[UserTable.email] = email
                user[UserTable.hashPassword] = passwordHash
                user[UserTable.name] = name
                user[UserTable.secondName] = name
                user[UserTable.type] = type
            }
        }
        return rowToUser(statement?.resultedValues?.get(0))
    }

    override suspend fun getUser(userId: Int): User? = dbQuery {
        UserTable.select { UserTable.id eq userId }.map { rowToUser(it) }.singleOrNull()
    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        UserTable.select { UserTable.email eq email }.map { rowToUser(it) }.singleOrNull()
    }

    override suspend fun getUsers(): List<User> = dbQuery {
        UserTable.selectAll().mapNotNull { rowToUser(it) }
    }

    override suspend fun updateUsers(
        userId: Int,
        email: String,
        passwordHash: String,
        name: String,
        secondName: String,
        type: String
    ): User? {
        val rowsUpdated = dbQuery {
            UserTable.update({ UserTable.id eq userId }) {
                it[UserTable.name] = name
                it[UserTable.secondName] = secondName
                it[UserTable.email] = email
                it[UserTable.type] = type
                passwordHash.let { hash -> it[UserTable.hashPassword] = hash }
            }
        }
        return if (rowsUpdated > 0) getUser(userId) else null
    }


    private fun rowToUser(row: ResultRow?): User? {
        if (row == null) {
            return null
        }
        return User(
            id = row[UserTable.id],
            name = row[UserTable.name],
            secondName = row[UserTable.secondName],
            email = row[UserTable.email],
            password = row[UserTable.hashPassword],
            type = row[UserTable.type]
        )
    }

}