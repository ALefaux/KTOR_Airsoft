package fr.alefaux.dto

data class Team(
    val id: String = "",
    val name: String = "",
    val acceptApplies: Boolean = true,

    val chief: User
)
