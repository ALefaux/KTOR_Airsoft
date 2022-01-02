package fr.alefaux.bdd.entities

import fr.alefaux.bdd.tables.Applies
import fr.alefaux.bdd.tables.Teams
import fr.alefaux.bdd.tables.Users
import fr.alefaux.models.Team
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class TeamEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TeamEntity>(Teams)

    var name by Teams.name
    var acceptApplies by Teams.acceptApplies

    var chief by UserEntity referencedOn Teams.chief
    val applies by ApplyEntity referrersOn Applies.team
    val members by UserEntity optionalReferrersOn Users.team

    fun toTeam(): Team {
        return Team(id.value, name, acceptApplies, chief.toUser(), members = members.map { it.toUser() })
    }

    fun toTeamWithApplies(): Team {
        return Team(id.value, name, acceptApplies, chief.toUser(), applies.map { it.toApplyWithUser() }, members.map { it.toUser() })
    }
}
