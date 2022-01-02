package fr.alefaux.bdd.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Applies: IntIdTable("apply") {
    val applier = reference("applier", Users)
    val team = reference("team", Teams)
}