package fr.alefaux.models

data class Team(
    val id : Int = -1,
    val name: String = "",
    val acceptApplies: Boolean = true,

    val chief: User? = null
)
