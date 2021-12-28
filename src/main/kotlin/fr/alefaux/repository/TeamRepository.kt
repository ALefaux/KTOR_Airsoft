package fr.alefaux.repository

import fr.alefaux.dto.Team
import fr.alefaux.models.Teams
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TeamRepository(private val userRepository: UserRepository) {

    fun getAll(filter: String): List<Team> = transaction {
        return@transaction Teams.select { Teams.name like filter }
            .map { toTeam(it) }
    }

    private fun getById(teamId: String): Team? = transaction {
        return@transaction Teams.select { Teams.id eq teamId }
            .map { toTeam(it) }
            .singleOrNull()
    }

    fun create(insertTeam: Team): Team? = transaction {
        val teamId: String = UUID.randomUUID().toString()
        Teams.insert {
            it[id] = teamId
            it[name] = insertTeam.name
            it[acceptApplies] = insertTeam.acceptApplies
            it[chiefId] = insertTeam.chief.id
        }
        return@transaction getById(teamId)
    }

    private fun toTeam(row: ResultRow): Team =
        Team(
            id = row[Teams.id],
            name = row[Teams.name],
            acceptApplies = row[Teams.acceptApplies],
            chief = userRepository.getById(row[Teams.chiefId])!!
        )

}