package fr.alefaux

import fr.alefaux.config.config
import fr.alefaux.di.repositoriesModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import fr.alefaux.plugins.*
import io.ktor.application.*
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    config()

    startKoin {
        modules(repositoriesModule)
    }

    configureMonitoring()
    configureSerialization()
    configureGraphQL(log)
}
