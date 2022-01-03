package fr.alefaux.routing

import fr.alefaux.models.Team
import fr.alefaux.services.models.ErrorReturned
import fr.alefaux.services.models.ServiceResult
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
            get("/{id}") {
                val teamId: Int? = call.parameters["id"]?.toIntOrNull()

                if(teamId != null) {
                    val result = teamService.getById(teamId)

                    if(result.isOk()) {
                        call.respond(HttpStatusCode.OK, result.data!!)
                    } else {
                        call.respond(HttpStatusCode.Gone)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            post {
                val insertTeam = call.receive<Team>()
                val result: ServiceResult<Team> = teamService.create(insertTeam)

                when (result.status) {
                    ServiceResult.Status.OK -> call.respond(HttpStatusCode.Created, result.data as Team)
                    ServiceResult.Status.NAME_EXISTS -> {
                        val error = ErrorReturned("This team name exists")
                        call.respond(HttpStatusCode.Conflict, error)
                    }
                    else -> call.respond(HttpStatusCode.Gone)
                }
            }
            put {
                val team = call.receive<Team>()
                val result = teamService.update(team)

                if(result.isOk()) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.Gone)
                }
            }
        }
    }
}