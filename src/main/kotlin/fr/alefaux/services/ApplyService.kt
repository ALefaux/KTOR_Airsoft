package fr.alefaux.services

import fr.alefaux.models.Apply
import fr.alefaux.models.CreateApply
import fr.alefaux.models.User
import fr.alefaux.repository.ApplyRepository
import fr.alefaux.repository.UserRepository
import fr.alefaux.services.models.ServiceResult

class ApplyService(
    private val applyRepository: ApplyRepository,
    private val userRepository: UserRepository
) {

    fun createApply(createApply: CreateApply): ServiceResult<Apply> {
        val result = applyRepository.create(createApply)

        return if (result.isOk()) {
            ServiceResult(data = result.data)
        } else {
            ServiceResult(ServiceResult.Status.ERROR)
        }
    }

    fun acceptApply(applyId: Int): ServiceResult<Boolean> {
        val apply = applyRepository.getById(applyId)

        return if(apply?.applier != null && apply.team != null) {
            val user: User = apply.applier
            user.team = apply.team

            if(userRepository.update(user)) {
                deleteApply(applyId)
            } else {
                ServiceResult(ServiceResult.Status.ERROR)
            }
        } else {
            ServiceResult(ServiceResult.Status.NOT_FOUND)
        }
    }

    fun declineApply(applyId: Int): ServiceResult<Boolean> {
        return if(applyRepository.delete(applyId)) {
            ServiceResult(ServiceResult.Status.OK)
        } else {
            ServiceResult(ServiceResult.Status.ERROR)
        }
    }

    fun deleteApply(applyId: Int): ServiceResult<Boolean> {
        return if(applyRepository.delete(applyId)) {
            ServiceResult(ServiceResult.Status.OK)
        } else {
            ServiceResult(ServiceResult.Status.ERROR)
        }
    }

}