package com.example.repository

import com.example.data.model.User
import com.example.data.model.event.Event
import com.example.data.model.organization.Organization
import com.example.data.model.prefs.PrefCommon
import com.example.data.model.prefs.PrefsUser

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

    suspend fun updateUserType(
        userId: Int,
        type: String
    ): Boolean


    suspend fun getPref(
        id: Int
    ): List<PrefsUser>

    suspend fun getEventPref(
        id: Int
    ): List<PrefsUser>

    suspend fun getPrefs(): List<PrefCommon?>

    suspend fun insertVolunteerPrefs(
        userId: Int,
        prefs: List<Int>
    )

    suspend fun insertVolunteer(
        userId: Int,
        description: String,
        phone: String,
        hours: Int,
        coins: Int,
        prefs: List<Int>
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

    suspend fun insertEvent(
        description: String,
        dateTime: String,
        creator: Int,
        hours: Int,
        coins: Int
    ): Event?

    suspend fun insertEventPrefs(
        eventId: Int,
        prefs: List<Int>
    )

    suspend fun deleteEvent(
        eventId: Int
    ): Boolean

    suspend fun getEvents(): List<Event>

    suspend fun getEvent(id: Int): Event


}