package fr.alefaux.repository

import fr.alefaux.bdd.entities.TeamEntity
import fr.alefaux.bdd.entities.UserEntity
import fr.alefaux.bdd.tables.Teams
import fr.alefaux.models.Team
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class TeamRepository {

    fun getAll(filter: String): List<Team> = transaction {
        return@transaction TeamEntity.all().filter { it.name.contains(filter) }.map { it.toTeamWithApplies() }.toList()
    }

    fun getById(teamId: Int): Team? = transaction {
        return@transaction TeamEntity.findById(teamId)?.toTeamWithApplies()
    }

    fun create(insertTeam: Team): Team? = transaction {
        if(insertTeam.chief?.id != null) {
            val userChief = UserEntity.findById(insertTeam.chief.id)
            if(userChief != null) {
                return@transaction TeamEntity.new {
                    name = insertTeam.name
                    acceptApplies = insertTeam.acceptApplies
                    chief = userChief
                }.toTeam()
            } else {
                return@transaction null
            }
        } else {
            return@transaction null
        }
    }

    fun update(team: Team): Boolean = transaction {
        val teamEntity = TeamEntity.findById(team.id)
        teamEntity?.name = team.name
        teamEntity?.acceptApplies = team.acceptApplies

        if(team.chief?.id != null) {
            val newChief = UserEntity.findById(team.chief.id)
            if (newChief != null) {
                teamEntity?.chief = newChief
            }
        }

        return@transaction teamEntity?.flush() ?: false
    }

    fun delete(teamId: Int): Boolean = transaction {
        TeamEntity.findById(teamId)?.delete()
        return@transaction TeamEntity.findById(teamId) == null
    }

    fun nameExists(name: String): Boolean = transaction {
        return@transaction Teams.select { Teams.name like "%$name%" }
            .singleOrNull() != null
    }

}