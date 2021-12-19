package fr.alefaux.di

import fr.alefaux.repository.UserRepository
import fr.alefaux.services.UserService
import org.koin.dsl.module

val repositoriesModule = module(createdAtStart = true) {
    // Repositories
    single { UserRepository() }

    // Services
    single { UserService(get()) }
}