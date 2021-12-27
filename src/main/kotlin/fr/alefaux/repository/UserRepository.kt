package fr.alefaux.repository

import fr.alefaux.dto.User
import fr.alefaux.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun getAll(): List<User> {
        return transaction {
            Users.selectAll().map { toUser(it) }
        }
    }

    fun getById(userId: String): User? {
        return transaction {
            Users.select { (Users.id eq userId) }
                .mapNotNull { toUser(it) }
                .singleOrNull()
        }
    }

    fun create(insertUser: User): User? {
        return transaction {
            Users.insert {
                it[id] = insertUser.id
                it[soldierName] = insertUser.soldierName
                it[imageUrl] = insertUser.imageUrl
            }
            getById(insertUser.id)
        }
    }

    private fun toUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            soldierName = row[Users.soldierName],
            imageUrl = row[Users.imageUrl]
        )

}