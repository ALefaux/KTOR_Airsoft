package fr.alefaux.services

import fr.alefaux.models.Team
import fr.alefaux.repository.TeamRepository
import fr.alefaux.services.models.ServiceResult

class TeamService(
    private val teamRepository: TeamRepository
) {

    fun getAll(filter: String): List<Team> = teamRepository.getAll(filter)

    fun getById(teamId: Int): ServiceResult<Team> {
        val team: Team? = teamRepository.getById(teamId)

        return if(team != null) {
            ServiceResult(data = team)
        } else {
            ServiceResult(ServiceResult.Status.NOT_FOUND)
        }
    }

    fun create(insertTeam: Team): ServiceResult<Team> {
        val nameExists = teamRepository.nameExists(insertTeam.name)
        return if (!nameExists) {
            val value = teamRepository.create(insertTeam)
            ServiceResult(data = value)
        } else {
            ServiceResult(ServiceResult.Status.NAME_EXISTS)
        }
    }

}