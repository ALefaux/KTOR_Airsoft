package fr.alefaux

import fr.alefaux.bdd.config.config
import fr.alefaux.di.repositoriesModule
import fr.alefaux.plugins.*
import fr.alefaux.plugins.routing.configureRouting
import io.ktor.application.*
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    config()

    startKoin {
        modules(repositoriesModule)
    }

    configureRouting()
    configureMonitoring()
    configureSerialization()
}