package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.core.domain.utils.Result
import javax.inject.Inject

class StoreControlledInfoUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(
        email: String,
        barcodeId: String,
        number: String
    ): Result<Unit> {
        val emailResult = dataStoreRepository.setEmail(email)
        val isSetupResult = dataStoreRepository.setIsSetupCompleted(true)
        val userTypeResult = dataStoreRepository.setUserType(AppSettings.UserType.controlled)
        val barcodeResult = dataStoreRepository.setBarcode(barcodeId)
        val numberResult = dataStoreRepository.setNumber(number)
        return when (emailResult && isSetupResult && userTypeResult && barcodeResult && numberResult) {
            true -> {
                Result.Success(Unit)
            }
            false -> Result.Error("Datastore Error")
        }
    }
}