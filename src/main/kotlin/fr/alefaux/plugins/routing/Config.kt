package fr.alefaux.plugins.routing

import io.ktor.application.*

fun Application.configureRouting() {
    userRouting()
    teamRouting()
}
