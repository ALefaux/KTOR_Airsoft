package fr.alefaux.plugins.routing

import fr.alefaux.models.Team
import fr.alefaux.services.models.ErrorReturned
import fr.alefaux.services.models.ReturnedService
import fr.alefaux.services.TeamService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

private const val TEAM_PATH = "/teams"

fun Application.teamRouting() {
    val teamService: TeamService by inject()
    routing {
        route(TEAM_PATH) {
            get {
                val filter: String = call.request.queryParameters["filter"] ?: ""
                val teams = teamService.getAll(filter)
                call.respond(HttpStatusCode.OK, teams)
            }
            post {
                val insertTeam = call.receive<Team>()
                val result: ReturnedService<Team> = teamService.create(insertTeam)

                when (result.status) {
                    ReturnedService.Status.OK -> call.respond(HttpStatusCode.Created, result.data as Team)
                    ReturnedService.Status.NAME_EXISTS -> {
                        val error = ErrorReturned("This team name exists")
                        call.respond(HttpStatusCode.Conflict, error)
                    }
                    else -> call.respond(HttpStatusCode.Gone)
                }
            }
        }
    }
}