package fr.alefaux

import fr.alefaux.config.config
import fr.alefaux.di.repositoriesModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import fr.alefaux.plugins.*
import io.ktor.application.*
import org.koin.core.context.startKoin

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        config()

        startKoin {
            modules(repositoriesModule)
        }

        configureMonitoring()
        configureSerialization()
        configureGraphQL(log)
    }.start(wait = true)
}
