package fr.alefaux.plugins

import com.apurebase.kgraphql.GraphQL
import fr.alefaux.graphql.userSchema
import fr.alefaux.services.UserService
import io.ktor.application.*
import org.koin.ktor.ext.inject
import org.slf4j.Logger

fun Application.configureGraphQL(logger: Logger) {
    val userService: UserService by inject()

    install(GraphQL) {
        playground = true
        schema {
            userSchema(userService, logger)
        }
    }
}