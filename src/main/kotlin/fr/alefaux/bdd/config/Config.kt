package fr.alefaux.bdd.config

import fr.alefaux.bdd.config.DataSource.devDataSource
import fr.alefaux.bdd.tables.Teams
import fr.alefaux.bdd.tables.Users
import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.config() {
    initDb()
}

fun initDb() {
    val dataSource = devDataSource()
    Database.connect(dataSource)

    transaction {
        SchemaUtils.create(Users, Teams)
    }
}