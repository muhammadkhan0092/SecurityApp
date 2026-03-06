package com.example.securityapp.modules.controller.domain.usecase

import com.example.securityapp.modules.controller.data.repository.FirebaseControllerRepository
import com.example.securityapp.modules.controller.domain.models.ControllerDomain
import com.example.securityapp.core.domain.utils.Result
import javax.inject.Inject

class SyncController @Inject constructor(
    private val firebaseControllerRepository: FirebaseControllerRepository
) {
    suspend operator fun invoke(domains: List<ControllerDomain>) {
        val roomDataResult = firebaseControllerRepository.getLocalData()
        when (roomDataResult) {
            is Result.Error<*> -> Unit
            is Result.Success -> {
                val data = roomDataResult.data
                val toDelete = data.filter { roomItem ->
                    domains.none { it.email == roomItem.email }
                }
                val toInsert = domains.filter { domainItem ->
                    data.none { it.email == domainItem.email }
                }
                firebaseControllerRepository.deleteData(toDelete)
                firebaseControllerRepository.insertData(toInsert)
            }
        }
    }
}