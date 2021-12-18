package fr.alefaux.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import fr.alefaux.dto.User
import fr.alefaux.services.UserService
import org.slf4j.Logger

fun SchemaBuilder.userSchema(userService: UserService, logger: Logger) {

    inputType<User> {
        description = "The input of the dessert without the identifier"
    }

    type<User> {
        description = "Dessert object with attributes name, description and imageUrl"
    }

    query("user") {
        resolver { userId: String ->
            try {
                return@resolver userService.getById(userId)
            } catch (e: Exception) {
                logger.error("Couldn't get user by id $userId", e)
                return@resolver null
            }
        }
    }

    query("userExist") {
        resolver { userId: String ->
            try {
                return@resolver userService.checkUserExist(userId)
            } catch (e: Exception) {
                logger.error("Can't check if user exist for id $userId", e)
                return@resolver null
            }
        }
    }

    mutation("createUser") {
        description = "Create a new user"
        resolver { insertUser: User ->
            try {
                return@resolver userService.add(insertUser)
            } catch (e: Exception) {
                logger.error("Couldn't create new user", e)
                return@resolver null
            }
        }
    }
}