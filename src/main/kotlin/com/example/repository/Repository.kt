package com.example.repository

import com.example.data.model.event.Event
import com.example.data.model.organization.Organization
import com.example.data.model.prefs.PrefCommon
import com.example.data.model.prefs.PrefsUser
import com.example.data.model.submission.Submission
import com.example.data.model.transactions.Transactions
import com.example.data.model.user.User
import com.example.data.model.volunteer.Volunteer

interface Repository {

    // users
    suspend fun checkEmailAvailable(email: String): Boolean

    suspend fun checkVolunteerOk(userID: Int): Boolean

    suspend fun checkOrganizationOk(userID: Int): Boolean

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
        coins: Int
    ): Volunteer?

    suspend fun getVolunteers(): List<Volunteer>

    suspend fun getVolunteerById(volunteerID: Int): Volunteer?

    suspend fun updateCoins(
        volunteerID: Int,
        coins: Int,
        option: String
    ): Boolean

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
        name: String,
        description: String,
        dateTime: String,
        creator: Int,
        hours: Int,
        coins: Int,
        city: String,
        place: String,
        methodEvent: String,
        roles: String,
        age: String,
        skills: String,
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

    suspend fun insertSubmission(
        eventID: Int,
        userFrom: Int,
        userTo: Int,
        letter: String,
        type: String,
        status: String
    ): Submission?

    suspend fun getSubmissionById(id: Int): Submission?

    suspend fun getSubmissions(): List<Submission>

    suspend fun deleteSubmission(
        submissionId: Int
    ): Boolean

    suspend fun updateSubmissionStatus(
        submissionId: Int,
        status: String
    ): Boolean

    suspend fun updateSubmissionType(
        submissionId: Int,
        type: String
    ): Boolean



    suspend fun insertTransaction(
        coins: Int,
        hours: Int,
        description: String,
        from: Int,
        to: Int,
        event: Int
    ): Transactions?

    suspend fun getTransaction(): List<Transactions>

    suspend fun getTransactionById(transactionID: Int): Transactions?

    suspend fun deleteTransaction(
        transactionID: Int
    ): Boolean

}