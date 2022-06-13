package com.example.repository

import com.example.data.model.event.Event
import com.example.data.model.organization.Organization
import com.example.data.model.prefs.PrefCommon
import com.example.data.model.prefs.PrefsUser
import com.example.data.model.submission.Submission
import com.example.data.model.transactions.Transactions
import com.example.data.model.user.User
import com.example.data.model.volunteer.Volunteer
import com.example.data.table.*
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

class RepositoryImpl: Repository {
    override suspend fun checkEmailAvailable(email: String): Boolean = dbQuery {
        UserTable.select { UserTable.email eq email }.map { rowToUser(it) }.isNullOrEmpty()
    }

    override suspend fun checkVolunteerOk(userID: Int): Boolean = dbQuery {
        Volunteers.select { Volunteers.userID eq userID }.map { rowToVolunteer(it) }.isNullOrEmpty()
    }

    override suspend fun checkOrganizationOk(userID: Int): Boolean = dbQuery {
        OrganizationTable.select { OrganizationTable.userID eq userID }.map { rowToOrganization(it) }.isNullOrEmpty()
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
                user[hashPassword] = passwordHash
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
                passwordHash.let { hash -> it[hashPassword] = hash }
            }
        }
        return if (rowsUpdated > 0) getUser(userId) else null
    }

    override suspend fun updateUserType(userId: Int, type: String): Boolean {
        dbQuery {
            UserTable.update({UserTable.id eq userId}) {
                it[UserTable.type] = type
            }
        }
        return true
    }

    override suspend fun getPref(id: Int): List<PrefsUser> = dbQuery {
        VolunteersPrefs.select{ VolunteersPrefs.user eq id}.map { rowToPref(it) }.toList()
    }

    override suspend fun getEventPref(id: Int): List<PrefsUser> = dbQuery {
        EventsPrefs.select{ EventsPrefs.event eq id}.map { rowToEventPrefs(it) }.toList()
    }

    override suspend fun getPrefs(): List<PrefCommon?> = dbQuery {
        Prefs.selectAll().map { rowToPrefs(it) }
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
    ): Volunteer? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = Volunteers.insert {
                it[Volunteers.userID] = userId
                it[Volunteers.description] = description
                it[Volunteers.phone] = phone
                it[Volunteers.coins] = coins
                it[Volunteers.hours] = hours
            }
            UserTable.update({UserTable.id eq userId}) {
                it[type] = "Volunteer"
            }
        }
        return rowToVolunteer(statement?.resultedValues?.get(0))
    }


    override suspend fun getVolunteers(): List<Volunteer> = dbQuery {
        Volunteers.selectAll().mapNotNull { rowToVolunteer(it) }
    }

    override suspend fun getVolunteerById(volunteerID: Int): Volunteer? = dbQuery {
        Volunteers.select { Volunteers.userID eq volunteerID }.map { rowToVolunteer(it) }.singleOrNull()
    }

    override suspend fun updateCoins(volunteerID: Int, coins: Int, option: String): Boolean {
        dbQuery {
            val volunteerOld = Volunteers.select { Volunteers.id eq volunteerID }.map { rowToVolunteer(it) }.singleOrNull()!!
            Volunteers.update({Volunteers.id eq volunteerID}) {
                if(option == "plus") {
                    it[Volunteers.coins] = volunteerOld.coins + coins
                }
                else {
                    it[Volunteers.coins] = volunteerOld.coins - coins

                }
            }
        }
        return true
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
            UserTable.update({UserTable.id eq userId}) {
                it[type] = "Organisation"
            }
        }
        return rowToOrganization(statement?.resultedValues?.get(0))
    }

    override suspend fun getOrganizations(): List<Organization> = dbQuery {
        OrganizationTable.selectAll().mapNotNull { rowToOrganization(it) }
    }

    override suspend fun getOrganizationById(id: Int): Organization? = dbQuery {
        OrganizationTable.select { OrganizationTable.userID eq id }.map { rowToOrganization(it) }.singleOrNull()
    }

    override suspend fun insertEvent(
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
    ): Event {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement =  Events.insert {
                it[Events.name] = name
                it[Events.description] = description
                it[Events.dateTime] = dateTime
                it[Events.creator] = creator
                it[Events.hours] = hours
                it[Events.coins] = coins
                it[Events.city] = city
                it[Events.place] = place
                it[Events.methodEvent] = methodEvent
                it[Events.roles] = roles
                it[Events.age] = age
                it[Events.skills] = skills
            }
        }
        return rowToEvent(statement?.resultedValues?.get(0)!!)
    }

    override suspend fun insertEventPrefs(eventId: Int, prefs: List<Int>) = dbQuery {
        for(i in prefs.indices) {
            EventsPrefs.insert {
                it[event] = eventId
                it[pref] = prefs[i]
            }
        }
    }

    override suspend fun deleteEvent(eventId: Int): Boolean = dbQuery {
        EventsPrefs.deleteWhere{ EventsPrefs.event eq eventId}
        Events.deleteWhere { Events.id eq eventId }
        return@dbQuery true
    }

    override suspend fun getEvents(): List<Event> = dbQuery {
        Events.selectAll().mapNotNull { rowToEvent(it) }
    }

    override suspend fun getEvent(id: Int): Event = dbQuery {
        Events.select { Events.id eq id }.map { rowToEvent(it) }.single()
    }

    override suspend fun insertSubmission(
        eventID: Int,
        userFrom: Int,
        userTo: Int,
        letter: String,
        type: String,
        status: String
    ): Submission? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement =  SubmissionTable.insert {
                it[SubmissionTable.eventID] = eventID
                it[SubmissionTable.userFrom] = userFrom
                it[SubmissionTable.userTo] = userTo
                it[SubmissionTable.letter] = letter
                it[SubmissionTable.type] = type
                it[SubmissionTable.status] = status
            }
        }
        return rowToSubmission(statement?.resultedValues?.get(0))
    }


    override suspend fun getSubmissionById(id: Int): Submission? = dbQuery {
        SubmissionTable.select { SubmissionTable.id eq id }.map { rowToSubmission(it) }.singleOrNull()
    }

    override suspend fun getSubmissions(): List<Submission>  = dbQuery {
        SubmissionTable.selectAll().mapNotNull { rowToSubmission(it) }
    }

    override suspend fun deleteSubmission(submissionId: Int): Boolean = dbQuery {
        SubmissionTable.deleteWhere { SubmissionTable.id eq submissionId }
        return@dbQuery true
    }

    override suspend fun updateSubmissionStatus(submissionId: Int, status: String): Boolean {
        dbQuery {
            SubmissionTable.update({SubmissionTable.id eq submissionId}) {
                it[SubmissionTable.status] = status
            }
        }
        return true
    }

    override suspend fun updateSubmissionType(submissionId: Int, type: String): Boolean {
        dbQuery {
            SubmissionTable.update({SubmissionTable.id eq submissionId}) {
                it[SubmissionTable.type] = type
            }
        }
        return true
    }

    override suspend fun insertTransaction(
        coins: Int,
        hours: Int,
        description: String,
        from: Int,
        to: Int,
        event: Int
    ): Transactions? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement =  TransactionTable.insert {
                it[TransactionTable.coins] = coins
                it[TransactionTable.hours] = hours
                it[TransactionTable.description] = description
                it[TransactionTable.from] = from
                it[TransactionTable.to] = to
                it[TransactionTable.event] = event
            }
        }
        return rowToTransaction(statement?.resultedValues?.get(0))
    }


    override suspend fun getTransaction(): List<Transactions> = dbQuery {
        TransactionTable.selectAll().mapNotNull { rowToTransaction(it) }
    }

    override suspend fun getTransactionById(transactionID: Int): Transactions? = dbQuery {
        TransactionTable.select { TransactionTable.id eq transactionID }.map { rowToTransaction(it) }.singleOrNull()
    }


    override suspend fun deleteTransaction(transactionID: Int): Boolean = dbQuery {
        TransactionTable.deleteWhere { TransactionTable.id eq transactionID }
        return@dbQuery true
    }

    private fun rowToVolunteer(row: ResultRow?): Volunteer? {
        if(row == null){
            return null
        }
        return Volunteer(
            id = row[Volunteers.id].value,
            userId = row[Volunteers.userID],
            description = row[Volunteers.description],
            phone = row[Volunteers.phone],
            hours = row[Volunteers.hours],
            coins = row[Volunteers.coins]
        )
    }

    private fun rowToSubmission(row: ResultRow?): Submission?{
        if(row == null){
            return null
        }
        return Submission(
            id = row[SubmissionTable.id],
            eventID = row[SubmissionTable.eventID],
            userTo = row[SubmissionTable.userTo],
            userFrom = row[SubmissionTable.userFrom],
            letter = row[SubmissionTable.letter],
            type = row[SubmissionTable.type],
            status = row[SubmissionTable.status]
        )
    }

    private fun rowToTransaction(row: ResultRow?): Transactions?{
        if(row == null){
            return null
        }
        return Transactions(
            id = row[TransactionTable.id],
            coins = row[TransactionTable.coins],
            hours = row[TransactionTable.hours],
            description = row[TransactionTable.description],
            from = row[TransactionTable.from],
            to = row[TransactionTable.to],
            event = row[TransactionTable.event]
        )
    }

    private fun rowToPrefs(row: ResultRow?): PrefCommon?{
        if(row == null){
            return null
        }
        return PrefCommon(
            name = row[Prefs.name]
        )
    }

    private fun rowToEventPrefs(row: ResultRow): PrefsUser{
        return PrefsUser(
            pref = row[EventsPrefs.pref].value
        )
    }

    private fun rowToEvent(row: ResultRow): Event {
        return Event(
            id = row[Events.id].value,
            name = row[Events.name],
            description = row[Events.description],
            dateTime = row[Events.dateTime],
            creator = row[Events.creator],
            hours = row[Events.hours],
            coins = row[Events.coins],
            city = row[Events.city],
            place = row[Events.place],
            methodEvent = row[Events.methodEvent],
            roles = row[Events.roles],
            age = row[Events.age],
            skills = row[Events.skills]

        )
    }

    private fun rowToPref(row: ResultRow): PrefsUser {
        return PrefsUser(
            pref = row[VolunteersPrefs.pref].value
        )
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