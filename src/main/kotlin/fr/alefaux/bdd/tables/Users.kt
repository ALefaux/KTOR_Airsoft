package fr.alefaux.bdd.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Users: IntIdTable(name = "user") {
    val externalId: Column<String> = varchar("external_id", 36)
    val soldierName: Column<String> = varchar("soldier_name", 50)
    val imageUrl: Column<String> = varchar("image_url", 256)
}