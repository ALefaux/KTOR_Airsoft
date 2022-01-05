package fr.alefaux.services

import fr.alefaux.models.User
import fr.alefaux.repository.TeamRepository
import fr.alefaux.repository.UserRepository
import fr.alefaux.services.models.ServiceResult

class UserService(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository
) {

    fun create(inputUser: User): ServiceResult<User> {
        val soldierNameExists: Boolean = userRepository.soldierNameExists(inputUser.soldierName)
        return if (!soldierNameExists) {
            val value = userRepository.create(inputUser)
            ServiceResult(data = value)
        } else {
            ServiceResult(ServiceResult.Status.NAME_EXISTS)
        }
    }

    fun getById(userId: Int): User? {
        val user = userRepository.getById(userId)

        if (user != null && user.team == null) {
            val team = teamRepository.findTeamFromChiefId(user.id)
            user.team = team
        }

        return user
    }

    fun getAll(): List<User> {
        return userRepository.getAll()
    }

}