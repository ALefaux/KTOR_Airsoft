package fr.alefaux.config

import fr.alefaux.config.DataSource.devDataSource
import fr.alefaux.models.Users
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
        SchemaUtils.create(Users)
    }
}