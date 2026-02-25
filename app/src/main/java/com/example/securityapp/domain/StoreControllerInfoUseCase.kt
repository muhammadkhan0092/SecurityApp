package com.example.securityapp.domain

import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.utils.Result
import javax.inject.Inject

class StoreControllerInfoUseCase @Inject constructor(
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation
) {
    suspend operator fun invoke(
        email : String
    ): Result<Unit> {
        val emailResult = dataStoreRepositoryImplementation.setEmail(email)
        val isSetupResult = dataStoreRepositoryImplementation.setIsSetupCompleted(true)
        val userTypeResult = dataStoreRepositoryImplementation.setUserType(AppSettings.UserType.controller)
        return when (emailResult && isSetupResult && userTypeResult) {
            true -> {
                Result.Success(Unit)
            }
            false -> Result.Error("Datastore Error")
        }
    }
}