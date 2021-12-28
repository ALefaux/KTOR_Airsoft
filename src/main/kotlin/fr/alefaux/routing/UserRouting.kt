package fr.alefaux.routing

import fr.alefaux.dto.User
import fr.alefaux.services.ErrorReturned
import fr.alefaux.services.ReturnedService
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
            get {
                val users: List<User> = userService.getAll()
                call.respond(HttpStatusCode.OK, users)
            }
            post {
                val inputUser = call.receive<User>()
                val result = userService.create(inputUser)

                when (result.status) {
                    ReturnedService.Status.OK -> call.respond(HttpStatusCode.Created, result.data!!)
                    ReturnedService.Status.NAME_EXISTS -> {
                        val error = ErrorReturned("This soldier name exists")
                        call.respond(HttpStatusCode.Conflict, error)
                    }
                    else -> call.respond(HttpStatusCode.Gone)
                }
            }
            get("/{id}") {
                val userId = call.parameters["id"]

                if (!userId.isNullOrBlank()) {
                    val user = userService.getById(userId)

                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } else {
                    // Todo make better message
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}
