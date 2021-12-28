package fr.alefaux.routing

import io.ktor.application.*

fun Application.configureRouting() {
    userRouting()
    teamRouting()
}
