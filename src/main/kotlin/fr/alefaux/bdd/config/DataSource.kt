package fr.alefaux.bdd.config

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*

object DataSource {
    fun devDataSource(): HikariDataSource {
        val appConfig = HoconApplicationConfig(ConfigFactory.load())
        val dbUrl = appConfig.property("ktor.db.uri").getString()

        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = dbUrl
            validate()
        }
        return HikariDataSource(config)
    }
}