package com.example.securityapp.modules.packages.domain

import com.example.securityapp.modules.app_settings.data.AppAppSettingsRepoImpl
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.uninstall.domain.UninstallDomainModel
import com.example.securityapp.modules.uninstall.domain.UninstallRepository
import javax.inject.Inject

class PackagesComplete @Inject constructor(
    private val appSettingsRepoImpl: AppAppSettingsRepoImpl,
    private val uninstallRepository: UninstallRepository
) {
    suspend operator fun invoke(packages : List<UninstallDomainModel>): Result<Unit> {
        val uninstallResult = uninstallRepository.insertData(packages)
        return when(uninstallResult){
            is Result.Error<*> -> Result.Error(uninstallResult.error)
            is Result.Success -> {
                when(appSettingsRepoImpl.setPackagesSet()){
                    true -> Result.Success(Unit)
                    false -> Result.Error("Error in Local Storage")
                }
            }
        }
    }
}