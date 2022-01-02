package fr.alefaux.models

data class Apply(
    val id: Int = -1,
    val applier: User? = null,
    val team: Team? = null
)
data class CreateApply(
    val teamId: Int,
    val applierId: Int
)