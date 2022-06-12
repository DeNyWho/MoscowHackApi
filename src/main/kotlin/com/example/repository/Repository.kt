package com.example.repository

import com.example.data.model.User
import com.example.data.model.organization.Organization
import com.example.data.table.OrganizationTable
import com.example.data.table.OrganizationTable.uniqueIndex
import org.jetbrains.exposed.sql.Column

interface Repository {

    // users
    suspend fun checkEmailAvailable(email: String): Boolean

    suspend fun createUser(
        email: String,
        passwordHash: String,
        name: String,
        secondName: String,
        type: String,
    ): User?

    suspend fun getUser(userId: Int): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun getUsers(): List<User>

    suspend fun updateUsers(
        userId: Int,
        email: String,
        passwordHash: String,
        name: String,
        secondName: String,
        type: String
    ): User?

    suspend fun insertVolunteerPrefs(
        userId: Int,
        prefs: List<Int>
    )

    suspend fun insertVolunteer(
        userId: Int,
        description: String,
        phone: String,
        hours: Int,
        coins: Int
    )
    suspend fun insertOrganization(
        userId: Int,
        email: String,
        name: String,
        description: String,
        site: String
    ): Organization?

    suspend fun getOrganizations(): List<Organization>

    suspend fun getOrganizationById(id: Int): Organization?

}