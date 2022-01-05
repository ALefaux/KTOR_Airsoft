package fr.alefaux.di

import fr.alefaux.repository.ApplyRepository
import fr.alefaux.repository.TeamRepository
import fr.alefaux.repository.UserRepository
import fr.alefaux.services.ApplyService
import fr.alefaux.services.TeamService
import fr.alefaux.services.UserService
import org.koin.dsl.module

val repositoriesModule = module(createdAtStart = true) {
    // Repositories
    single { UserRepository() }
    single { TeamRepository() }
    single { ApplyRepository() }

    // Services
    single { UserService(get()) }
    single { TeamService(get(), get()) }
    single { ApplyService(get(), get())}
}