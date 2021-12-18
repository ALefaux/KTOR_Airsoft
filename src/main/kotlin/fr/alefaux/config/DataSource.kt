package fr.alefaux.config

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*

object DataSource {
    fun devDataSource(): HikariDataSource {
        val appConfig = HoconApplicationConfig(ConfigFactory.load())
        val dbUrl = appConfig.property("ktor.db.uri").getString()
        val dbUser = appConfig.property("ktor.db.username").getString()
        val dbPassword = appConfig.property("ktor.db.password").getString()

        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = dbUrl
            username = dbUser
            password = dbPassword
            validate()
        }
        return HikariDataSource(config)
    }
}