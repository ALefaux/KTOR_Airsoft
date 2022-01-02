package fr.alefaux.bdd.entities

import fr.alefaux.bdd.tables.Applies
import fr.alefaux.models.Apply
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ApplyEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<ApplyEntity>(Applies)

    var applier by UserEntity referencedOn Applies.applier
    var team by TeamEntity referencedOn Applies.team

    fun toApply(): Apply {
        return Apply(id.value, applier.toUser(), team.toTeam())
    }

    fun toApplyWithUser(): Apply {
        return Apply(id.value, applier = applier.toUser())
    }

    fun toApplyWithTeam(): Apply {
        return Apply(id.value, team = team.toTeam())
    }
}