package fr.alefaux.bdd.entities

import fr.alefaux.bdd.tables.Users
import fr.alefaux.models.User
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class UserEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    var externalId by Users.externalId
    var soldierName by Users.soldierName
    var imageUrl by Users.imageUrl

    fun toUser(): User {
        return User(id.value, externalId, soldierName, imageUrl)
    }
}