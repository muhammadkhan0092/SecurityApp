package com.example.securityapp.modules.intro.domain

import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.core.domain.repository.UninstallRepository
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controlled.domain.models.UninstallDomain
import javax.inject.Inject

class PackagesComplete @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val uninstallRepository: UninstallRepository
) {
    suspend operator fun invoke(packages : List<UninstallDomain>): Result<Unit> {
        val uninstallResult = uninstallRepository.insertData(packages)
        return when(uninstallResult){
            is Result.Error<*> -> Result.Error(uninstallResult.error)
            is Result.Success -> {
                when(dataStoreRepository.setPackagesSet()){
                    true -> Result.Success(Unit)
                    false -> Result.Error("Error in Local Storage")
                }
            }
        }
    }
}