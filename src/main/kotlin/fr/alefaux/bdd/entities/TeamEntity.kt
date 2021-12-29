package fr.alefaux.bdd.entities

import fr.alefaux.bdd.tables.Teams
import fr.alefaux.models.Team
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class TeamEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<TeamEntity>(Teams)
    var name by Teams.name
    var acceptApplies by Teams.acceptApplies

    var chief by UserEntity referencedOn Teams.chief

    fun toTeam(): Team {
        return Team(id.value, name, acceptApplies, chief.toUser())
    }
}
