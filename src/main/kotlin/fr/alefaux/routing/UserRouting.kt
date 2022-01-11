package fr.alefaux.routing

import fr.alefaux.models.User
import fr.alefaux.services.models.ErrorReturned
import fr.alefaux.services.models.ServiceResult
import fr.alefaux.services.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

private const val USER_PATH = "/users"

fun Application.userRouting() {
    val userService: UserService by inject()
    routing {
        route(USER_PATH) {
            post {
                val inputUser = call.receive<User>()
                val result = userService.create(inputUser)

                when (result.status) {
                    ServiceResult.Status.OK -> call.respond(HttpStatusCode.Created, result.data!!)
                    ServiceResult.Status.NAME_EXISTS -> {
                        val error = ErrorReturned("This soldier name exists")
                        call.respond(HttpStatusCode.Conflict, error)
                    }
                    else -> call.respond(HttpStatusCode.Gone)
                }
            }
            get("/{id}") {
                val userId = call.parameters["id"]?.toIntOrNull()

                if (userId != null) {
                    val user = userService.getById(userId)

                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            get("/find/{externalId}") {
                val externalId: String? = call.parameters["externalId"]

                if(externalId != null) {
                    val user = userService.findByExternalId(externalId)

                    if(user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}
