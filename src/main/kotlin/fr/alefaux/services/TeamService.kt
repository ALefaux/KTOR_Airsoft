package fr.alefaux.services

import fr.alefaux.models.Team
import fr.alefaux.repository.TeamRepository
import fr.alefaux.services.models.ReturnedService

class TeamService(private val teamRepository: TeamRepository) {

    fun getAll(filter: String): List<Team> = teamRepository.getAll(filter)

    fun create(insertTeam: Team): ReturnedService<Team> {
        val nameExists = teamRepository.nameExists(insertTeam.name)
        return if(!nameExists) {
            val value = teamRepository.create(insertTeam)
            ReturnedService(data = value)
        } else {
            ReturnedService(ReturnedService.Status.NAME_EXISTS)
        }
    }

}