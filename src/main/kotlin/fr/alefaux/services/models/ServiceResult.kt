package fr.alefaux.services.models

data class ServiceResult<T>(
    val status: Status = Status.OK,
    val data: T? = null
) {
    enum class Status {
        OK, NAME_EXISTS, NO_RESULT, ERROR, NOT_FOUND
    }

    fun isOk(): Boolean = status == Status.OK

    fun isNotFound(): Boolean = status == Status.NOT_FOUND
}
