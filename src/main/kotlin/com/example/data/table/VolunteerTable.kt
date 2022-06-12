package com.example.data.table

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*

class Volunteer(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, Volunteer>(Volunteers)

    var userId by Volunteers.userID
    var prefs   by Pref via VolunteersPrefs
}

object Volunteers: IntIdTable() {
    val userID: Column<Int> = integer("user_id").autoIncrement()
    val description: Column<String> = varchar("description", 512)
    val phone: Column<String> = varchar("phone",200)
    val hours: Column<Int> = integer("hours")
    val coins: Column<Int> = integer("coins")
}
