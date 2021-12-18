package fr.alefaux.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users: Table(name = "user") {
    val id: Column<String> = varchar("id", 36).primaryKey()
    val soldierName: Column<String> = varchar("soldier_name", 50)
    val imageUrl: Column<String> = varchar("image_url", 256)
}