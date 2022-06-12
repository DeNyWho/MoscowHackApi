package com.example.repository

import com.example.data.model.User
import com.example.data.model.organization.Organization
import com.example.data.table.*
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

    override suspend fun insertVolunteerPrefs(userId: Int, prefs: List<Int>) = dbQuery {
        for(i in prefs.indices) {
            VolunteersPrefs.insert {
                it[user] = userId
                it[pref] = prefs[i]
            }
        }
    }

    override suspend fun insertVolunteer(
        userId: Int,
        description: String,
        phone: String,
        hours: Int,
        coins: Int
    ) {
        dbQuery {
            Volunteers.insert {
                it[userID] = userId
                it[Volunteers.description] = description
                it[Volunteers.phone] = phone
                it[Volunteers.coins] = coins
                it[Volunteers.hours] = hours
            }
        }
    }

    override suspend fun insertOrganization(
        userId: Int,
        email: String,
        name: String,
        description: String,
        site: String
    ): Organization? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement =  OrganizationTable.insert {
                it[userID] = userId
                it[OrganizationTable.email] = email
                it[OrganizationTable.name] = name
                it[OrganizationTable.description] = description
                it[OrganizationTable.site] = site
            }
        }
        return rowToOrganization(statement?.resultedValues?.get(0))
    }

    override suspend fun getOrganizations(): List<Organization> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrganizationById(id: Int): Organization? = dbQuery {
        OrganizationTable.select { OrganizationTable.id eq id }.map { rowToOrganization(it) }.singleOrNull()
    }

    private fun rowToOrganization(row: ResultRow?): Organization? {
        if(row == null){
            return null
        }
        return Organization(
            id = row[OrganizationTable.id],
            userId = row[OrganizationTable.userID],
            email = row[OrganizationTable.email],
            name = row[OrganizationTable.name],
            description = row[OrganizationTable.description],
            site = row[OrganizationTable.site]
        )
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