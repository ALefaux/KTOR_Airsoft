package fr.alefaux.models

data class Team(
    val id : Int = -1,
    val name: String = "",
    val acceptApplies: Boolean = true,

    val chief: User? = null,
    val applies: List<Apply>? = null,
    val members: List<User>? = null
)
data class CreateTeam(
    val name: String,
    val acceptApplies: Boolean,
    val chiefId: Int
)
