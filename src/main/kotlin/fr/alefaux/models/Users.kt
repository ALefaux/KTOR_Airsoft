package fr.alefaux.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users: Table(name = "user") {
    const val ID_LENGTH = 36

    val id: Column<String> = varchar("id", ID_LENGTH).primaryKey()
    val soldierName: Column<String> = varchar("soldier_name", 50)
    val imageUrl: Column<String> = varchar("image_url", 256)
}