package fr.alefaux.routing

import fr.alefaux.models.CreateApply
import fr.alefaux.services.ApplyService
import fr.alefaux.services.models.ServiceResult
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.applyRouting() {
    val applyService: ApplyService by inject()

    routing {
        route("/applies") {
            post {
                val createApply = call.receive<CreateApply>()
                val result = applyService.createApply(createApply)

                when (result.status) {
                    ServiceResult.Status.OK -> call.respond(HttpStatusCode.Created, result.data!!)
                    ServiceResult.Status.ERROR -> call.respond(HttpStatusCode.Conflict)
                    else -> call.respond(HttpStatusCode.Gone)
                }
            }
            get("/{id}/accept") {
                val applyId = call.parameters["id"]?.toIntOrNull()

                if (applyId != null) {
                    val result = applyService.acceptApply(applyId)

                    if (result.isOk()) {
                        call.respond(HttpStatusCode.OK)
                    } else if (result.isNotFound()) {
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        call.respond(HttpStatusCode.Gone)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            get("/{id}/decline") {
                val applyId = call.parameters["id"]?.toIntOrNull()

                if(applyId != null) {
                    val result = applyService.declineApply(applyId)

                    if(result.isOk()) {
                        call.respond(HttpStatusCode.OK)
                    } else {
                        call.respond(HttpStatusCode.Gone)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}