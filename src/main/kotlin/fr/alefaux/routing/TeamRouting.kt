package fr.alefaux.routing

import fr.alefaux.dto.Team
import fr.alefaux.services.ErrorReturned
import fr.alefaux.services.ReturnedService
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
                val filterParameter: String = call.request.queryParameters["filter"] ?: ""
                val filter = "%$filterParameter%"
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