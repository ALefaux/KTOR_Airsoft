package fr.alefaux.plugins

import fr.alefaux.routing.applyRouting
import fr.alefaux.routing.teamRouting
import fr.alefaux.routing.userRouting
import io.ktor.application.*

fun Application.configureRouting() {
    userRouting()
    teamRouting()
    applyRouting()
}
