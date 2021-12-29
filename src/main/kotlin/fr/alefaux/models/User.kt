package fr.alefaux.models

data class User(
    val id: Int = -1,
    val externalId: String = "",
    val soldierName: String = "",
    val imageUrl: String = ""
)