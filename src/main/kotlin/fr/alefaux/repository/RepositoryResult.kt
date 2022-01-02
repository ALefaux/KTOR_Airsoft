package fr.alefaux.repository

data class RepositoryResult<T>(
    val type: Type = Type.OK,
    val data: T? = null,
    val message: String? = null
) {
    enum class Type {
        OK, NOT_FOUND, EXISTS, MISSING_DATA
    }

    fun isOk(): Boolean = type == Type.OK
}
