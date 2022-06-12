package com.example.data.table

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Prefs: IntIdTable() {
    val name = varchar("name", 50)
}

class Pref(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Pref>(Prefs)
    var name by Prefs.name
}

object VolunteersPrefs : Table() {
    val user = reference("user", Volunteers)
    val pref = reference("pref", Prefs)
    override val primaryKey = PrimaryKey(user, pref, name = "PK_User_Pref")
}