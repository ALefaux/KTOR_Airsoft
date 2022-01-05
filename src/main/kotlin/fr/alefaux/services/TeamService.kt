package fr.alefaux.services

import fr.alefaux.models.CreateTeam
import fr.alefaux.models.Team
import fr.alefaux.repository.TeamRepository
import fr.alefaux.repository.UserRepository
import fr.alefaux.services.models.ServiceResult

class TeamService(
    private val teamRepository: TeamRepository,
    private val userRepository: UserRepository
) {

    fun getAll(filter: String): List<Team> = teamRepository.getAll(filter)

    fun getById(teamId: Int): ServiceResult<Team> {
        val team: Team? = teamRepository.getById(teamId)

        return if (team != null) {
            ServiceResult(data = team)
        } else {
            ServiceResult(ServiceResult.Status.NOT_FOUND)
        }
    }

    fun create(insertTeam: CreateTeam): ServiceResult<Team> {
        val nameExists = teamRepository.nameExists(insertTeam.name)
        return if (!nameExists) {
            val value = teamRepository.create(insertTeam)
            ServiceResult(data = value)
        } else {
            ServiceResult(ServiceResult.Status.NAME_EXISTS)
        }
    }

    fun update(team: Team): ServiceResult<Boolean> {
        return if (teamRepository.update(team)) {
            ServiceResult(ServiceResult.Status.OK)
        } else {
            ServiceResult(ServiceResult.Status.ERROR)
        }
    }

    fun delete(teamId: Int): ServiceResult<Boolean> {
        return if(teamRepository.delete(teamId)) {
            ServiceResult(ServiceResult.Status.OK)
        } else {
            ServiceResult(ServiceResult.Status.ERROR)
        }
    }

    fun removeUser(teamId: Int, userId: Int): ServiceResult<Boolean> {
        val teamFound = teamRepository.getById(teamId)

        return if(teamFound != null && teamFound.members?.any { it.id == userId } == true) {
            val result = userRepository.removeTeamForUser(userId)

            if(result) {
                ServiceResult(ServiceResult.Status.OK)
            } else {
                ServiceResult(ServiceResult.Status.ERROR)
            }
        } else {
            ServiceResult(ServiceResult.Status.ERROR)
        }
    }

}