package com.example.repository

import com.example.data.model.User

interface Repository {
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



}