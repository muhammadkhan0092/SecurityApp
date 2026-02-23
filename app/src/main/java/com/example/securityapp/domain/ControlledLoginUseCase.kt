package com.example.securityapp.domain

import android.util.Log
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.DeviceRepository
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.firebase.DtoDevice
import com.example.securityapp.modules.controlled.PhoneRepository
import javax.inject.Inject
import com.example.securityapp.utils.Result

class ControlledLoginUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository,
    private val deviceRepository: DeviceRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation
) {
    suspend operator fun invoke(email: String, password: String,id : String): Result<Unit> {
        val isAirplaneOn = phoneRepository.isAirplaneModeOn()
        if (isAirplaneOn) {
            return Result.Error("Disable Airplane Mode")
        }
        val sims = phoneRepository.getSimNumbers()
        if (sims.isEmpty()) {
            return Result.Error("Insert A Sim to Continue")
        }
        val result = deviceRepository.insertDeviceInfo(
            DtoDevice(
                email = email,
                password = password,
                barcodeId = id,
                phoneNumbers = emptyList(),
                connectedDevices = emptyList()
            )
        )
        return when (result) {
            is Result.Error<*> -> {
                Result.Error(result.error)
            }
            is Result.Success<*> -> {
                val emailResult = dataStoreRepositoryImplementation.setEmail(email)
                val isSetupResult = dataStoreRepositoryImplementation.setIsSetupCompleted(true)
                val userTypeResult =
                    dataStoreRepositoryImplementation.setUserType(AppSettings.UserType.controlled)
                val barcodeResult = dataStoreRepositoryImplementation.setBarcode(id)
                Log.d("KHAN", "SUCCESS")
                when (emailResult && isSetupResult && userTypeResult && barcodeResult) {
                    true -> {
                        Result.Success(Unit)
                    }
                    false -> Result.Error("Datastore Error")
                }
            }
        }
    }
}