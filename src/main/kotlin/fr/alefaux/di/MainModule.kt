package fr.alefaux.di

import fr.alefaux.repository.UserRepository
import org.koin.dsl.module

val repositoriesModule = module(createdAtStart = true) {
    single { UserRepository() }
}