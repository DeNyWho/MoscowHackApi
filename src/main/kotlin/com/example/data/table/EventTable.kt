package com.example.data.table

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

class Event(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, Event>(Events)

    var eventId by Events.id
    var prefs   by Pref via EventsPrefs
}

object Events: IntIdTable() {
    val description: Column<String> = varchar("description", 512)
    val dateTime: Column<String> = varchar("dateTime", 512)
    val creator: Column<Int> = integer("creator_id")
    val hours: Column<Int> = integer("hours")
    val coins: Column<Int> = integer("coins")
}
