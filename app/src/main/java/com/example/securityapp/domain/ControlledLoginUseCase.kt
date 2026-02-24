package com.example.securityapp.domain

import com.example.securityapp.core.data.repository.DeviceRepository
import com.example.securityapp.firebase.DtoDevice
import com.example.securityapp.modules.controlled.PhoneRepository
import com.example.securityapp.utils.Result
import javax.inject.Inject

class ControlledLoginUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository,
    private val deviceRepository: DeviceRepository,
    private val storeControlledInfoUseCase: StoreControlledInfoUseCase
) {
    suspend operator fun invoke(email: String, password: String, id: String): Result<Unit> {
        val isAirplaneOn = phoneRepository.isAirplaneModeOn()
        if (isAirplaneOn) {
            return Result.Error("Disable Airplane Mode")
        }
        val sims = phoneRepository.getSimNumbers()
        if (sims.isEmpty()) {
            return Result.Error("Insert A Sim to Continue")
        }
        val alreadyDevice = deviceRepository.getDevice(email)
        when (alreadyDevice) {
            is Result.Error<*> -> return Result.Error("Server Error")
            is Result.Success -> {
                when (alreadyDevice.data) {
                    null -> Unit
                    else -> {
                        return when (alreadyDevice.data.password == password) {
                            true -> Result.Success(Unit)
                            false -> Result.Error("Invalid Password")
                        }
                    }
                }
            }
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
                storeControlledInfoUseCase(email, id)
            }
        }
    }
}