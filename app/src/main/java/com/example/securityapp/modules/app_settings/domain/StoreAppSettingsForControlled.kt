package com.example.securityapp.modules.app_settings.domain

import com.example.securityapp.modules.app_settings.data.AppAppSettingsRepoImpl
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.datastore.AppSettings
import javax.inject.Inject

class StoreAppSettingsForControlled @Inject constructor(
    private val appSettingsRepoImpl: AppAppSettingsRepoImpl
) {
    suspend operator fun invoke(
        email: String,
        barcodeId: String,
        number: String
    ): Result<Unit> {
        val emailResult = appSettingsRepoImpl.setEmail(email)
        val isSetupResult = appSettingsRepoImpl.setIsSetupCompleted(true)
        val userTypeResult = appSettingsRepoImpl.setUserType(AppSettings.UserType.controlled)
        val barcodeResult = appSettingsRepoImpl.setBarcode(barcodeId)
        val numberResult = appSettingsRepoImpl.setNumber(number)
        return when (emailResult && isSetupResult && userTypeResult && barcodeResult && numberResult) {
            true -> {
                Result.Success(Unit)
            }
            false -> Result.Error("Datastore Error")
        }
    }
}