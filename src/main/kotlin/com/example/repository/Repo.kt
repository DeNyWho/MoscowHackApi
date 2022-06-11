package com.example.repository

import com.example.data.model.User
import com.example.data.table.UserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class Repo {

    suspend fun addUser(user: User) {
        DatabaseFactory.dbQuery {
            UserTable.insert { ut ->
                ut[email] = user.email
                ut[hashPassword] = user.password
                ut[name] = user.name
                ut[secondName] = user.secondName
                ut[type] = user.type
            }
        }
    }

    suspend fun findByUserEmail(email: String) = DatabaseFactory.dbQuery {
        UserTable.select { UserTable.email.eq(email) }
            .map { rowToUser(it) }
            .singleOrNull()
    }

    private fun rowToUser(row: ResultRow?): User?{
        if(row == null){
            return null
        }

        return User(
            email =  row[UserTable.email],
            password = row[UserTable.hashPassword],
            name = row[UserTable.name],
            secondName = row[UserTable.secondName],
            type = row[UserTable.type]
        )
    }

}