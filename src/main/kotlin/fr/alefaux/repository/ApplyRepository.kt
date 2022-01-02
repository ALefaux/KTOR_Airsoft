package fr.alefaux.repository

import fr.alefaux.bdd.entities.ApplyEntity
import fr.alefaux.bdd.entities.TeamEntity
import fr.alefaux.bdd.entities.UserEntity
import fr.alefaux.models.Apply
import fr.alefaux.models.CreateApply
import org.jetbrains.exposed.sql.transactions.transaction

class ApplyRepository {

    fun create(createApply: CreateApply): RepositoryResult<Apply> = transaction {
        val teamFound: TeamEntity? = TeamEntity.findById(createApply.teamId)

        // todo missing member check

        if (teamFound != null) {
            if (teamFound.applies.none { it.applier.id.value == createApply.applierId }) {
                val applierFound: UserEntity? = UserEntity.findById(createApply.applierId)

                if (applierFound != null) {
                    val result = ApplyEntity.new {
                        team = teamFound
                        applier = applierFound
                    }.toApply()
                    return@transaction RepositoryResult(data = result)
                } else {
                    return@transaction RepositoryResult(RepositoryResult.Type.NOT_FOUND, message = "User not found")
                }
            } else {
                return@transaction RepositoryResult(RepositoryResult.Type.EXISTS, message = "User has already apply")
            }
        } else {
            return@transaction RepositoryResult(RepositoryResult.Type.NOT_FOUND, message = "Team not found")
        }
    }

    fun getById(applyId: Int): Apply? = transaction {
        return@transaction ApplyEntity.findById(applyId)?.toApply()
    }

    fun delete(applyId: Int): Boolean = transaction {
        ApplyEntity.findById(applyId)?.delete()
        return@transaction ApplyEntity.findById(applyId) == null
    }

}