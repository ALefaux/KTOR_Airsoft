package fr.alefaux.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Teams: Table(name = "team") {
    val id: Column<String> = varchar("id", length = 36).primaryKey()
    val name: Column<String> = varchar("name", length = 50)
    val acceptApplies: Column<Boolean> = bool("accept_applies")

    val chiefId: Column<String> = varchar("chief_id", Users.ID_LENGTH) references Users.id
}