package fr.alefaux.repository

import fr.alefaux.bdd.entities.UserEntity
import fr.alefaux.bdd.tables.Users
import fr.alefaux.models.User
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun getAll(): List<User> = transaction {
        UserEntity.all().map { it.toUser() }.toList()
    }

    fun getById(userId: Int): User? = transaction {
        UserEntity.findById(userId)?.toUser()
    }

    fun create(insertUser: User): User {
        return transaction {
            UserEntity.new {
                externalId = insertUser.externalId
                soldierName = insertUser.soldierName
                imageUrl = insertUser.imageUrl
            }.toUser()
        }
    }

    fun soldierNameExists(soldierName: String): Boolean = transaction {
        return@transaction UserEntity.find { Users.soldierName like "%$soldierName%" }
            .singleOrNull() != null
    }

}