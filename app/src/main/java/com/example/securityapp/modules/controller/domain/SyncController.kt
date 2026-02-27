package com.example.securityapp.modules.controller.domain

import com.example.securityapp.modules.controller.data.repository.ControllerRepository
import com.example.securityapp.utils.Result
import javax.inject.Inject

class SyncController @Inject constructor(
    private val controllerRepository: ControllerRepository
) {
    suspend operator fun invoke(domains: List<ControllerDomain>) {
        val roomDataResult = controllerRepository.getLocalData()
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
                controllerRepository.deleteData(toDelete)
                controllerRepository.insertData(toInsert)
            }
        }
    }
}