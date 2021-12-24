package fr.alefaux.services

import fr.alefaux.dto.User
import fr.alefaux.repository.UserRepository

class UserService(private val userRepository: UserRepository) {

    fun add(inputUser: User): User? {
        return userRepository.create(inputUser)
    }

    fun checkUserExist(userId: String): Boolean {
        return userRepository.getById(userId) != null
    }

    fun getById(userId: String): User? {
        return userRepository.getById(userId)
    }

    fun getAll(): List<User> {
        return userRepository.getAll()
    }

}