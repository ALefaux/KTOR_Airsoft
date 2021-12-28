package fr.alefaux.services

import fr.alefaux.dto.User
import fr.alefaux.repository.UserRepository

class UserService(private val userRepository: UserRepository) {

    fun create(inputUser: User): ReturnedService<User> {
        val soldierNameExists: Boolean = userRepository.soldierNameExists(inputUser.soldierName)
        return if (!soldierNameExists) {
            val value = userRepository.create(inputUser)
            ReturnedService(data = value)
        } else {
            ReturnedService(ReturnedService.Status.NAME_EXISTS)
        }
    }

    fun getById(userId: String): User? {
        return userRepository.getById(userId)
    }

    fun getAll(): List<User> {
        return userRepository.getAll()
    }

}