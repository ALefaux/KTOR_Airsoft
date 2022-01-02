package fr.alefaux.services

import fr.alefaux.models.Apply
import fr.alefaux.models.CreateApply
import fr.alefaux.repository.ApplyRepository
import fr.alefaux.services.models.ServiceResult

class ApplyService(
    private val applyRepository: ApplyRepository
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

        return if(apply != null) {
            // todo ajouter un membre
            val deleted = applyRepository.delete(applyId)

            if(deleted) {
                ServiceResult(ServiceResult.Status.OK)
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

}