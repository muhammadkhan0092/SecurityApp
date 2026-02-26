package com.example.securityapp.modules.controlled.domain

import com.example.securityapp.modules.controlled.data.ControlledRepository
import com.example.securityapp.modules.controller.data.ControllerRepository
import com.example.securityapp.modules.controller.domain.ControllerDomain
import com.example.securityapp.utils.Result
import javax.inject.Inject

class SyncControlled @Inject constructor(
    private val controlledRepository: ControlledRepository
) {
    suspend operator fun invoke(domains: List<ControlledDomain>) {
        val roomDataResult = controlledRepository.getLocalData()
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
                controlledRepository.deleteData(toDelete)
                controlledRepository.insertData(toInsert)
            }
        }
    }
}