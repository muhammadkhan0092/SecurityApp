package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.modules.controlled.data.repository.FirebaseControlledRepository
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.core.domain.utils.Result
import javax.inject.Inject

class SyncControlled @Inject constructor(
    private val firebaseControlledRepository: FirebaseControlledRepository
) {
    suspend operator fun invoke(domains: List<ControlledDomain>) {
        val roomDataResult = firebaseControlledRepository.getLocalData()
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
                firebaseControlledRepository.deleteData(toDelete)
                firebaseControlledRepository.insertData(toInsert)
            }
        }
    }
}