package fr.alefaux.services

import fr.alefaux.dto.Team
import fr.alefaux.repository.TeamRepository

class TeamService(private val teamRepository: TeamRepository) {

    fun getAll(filter: String): List<Team> = teamRepository.getAll(filter)

    fun create(insertTeam: Team): Team? = teamRepository.create(insertTeam)

}