package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.utils.Result
import javax.inject.Inject

class StoreControlledInfoUseCase @Inject constructor(
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation
) {
    suspend operator fun invoke(
        email : String,
        barcodeId : String
    ): Result<Unit> {
        val emailResult = dataStoreRepositoryImplementation.setEmail(email)
        val isSetupResult = dataStoreRepositoryImplementation.setIsSetupCompleted(true)
        val userTypeResult = dataStoreRepositoryImplementation.setUserType(AppSettings.UserType.controlled)
        val barcodeResult = dataStoreRepositoryImplementation.setBarcode(barcodeId)
        return when (emailResult && isSetupResult && userTypeResult && barcodeResult) {
            true -> {
                Result.Success(Unit)
            }
            false -> Result.Error("Datastore Error")
        }
    }
}