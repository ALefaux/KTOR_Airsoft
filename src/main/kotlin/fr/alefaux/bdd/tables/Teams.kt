package fr.alefaux.bdd.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Teams: IntIdTable(name = "team") {
    val name: Column<String> = varchar("name", length = 50)
    val acceptApplies: Column<Boolean> = bool("accept_applies")

    val chief = reference("chief", Users)
}