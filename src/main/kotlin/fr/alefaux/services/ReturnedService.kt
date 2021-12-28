package fr.alefaux.services

data class ReturnedService<T>(
    val status: Status = Status.OK,
    val data: T? = null
) {
    enum class Status {
        OK, NAME_EXISTS, NO_RESULT
    }
}
