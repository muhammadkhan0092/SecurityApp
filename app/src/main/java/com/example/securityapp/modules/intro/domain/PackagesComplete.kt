package com.example.securityapp.modules.intro.domain

import android.util.Log
import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.modules.uninstall.domain.UninstallRepository
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.uninstall.domain.UninstallDomainModel
import javax.inject.Inject

class PackagesComplete @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val uninstallRepository: UninstallRepository
) {
    suspend operator fun invoke(packages : List<UninstallDomainModel>): Result<Unit> {
        Log.d("KHAN","INSERTING PACKAGES $packages")
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