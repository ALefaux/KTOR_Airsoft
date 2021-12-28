package fr.alefaux.routing

import fr.alefaux.dto.Team
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
                val teams = teamService.getAll()
                call.respond(HttpStatusCode.OK, teams)
            }
            post {
                val insertTeam = call.receive<Team>()
                val team = teamService.create(insertTeam)

                if(team != null) {
                    call.respond(HttpStatusCode.Created, team)
                } else {
                    call.respond(HttpStatusCode.Conflict)
                }
            }
        }
    }
}