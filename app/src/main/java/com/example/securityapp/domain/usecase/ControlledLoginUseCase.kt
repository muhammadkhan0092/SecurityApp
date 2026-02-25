package com.example.securityapp.domain.usecase

import com.example.securityapp.core.data.repository.ControlledDeviceInControlled
import com.example.securityapp.core.data.repository.ControlledRepository
import com.example.securityapp.core.data.repository.DeviceRepository
import com.example.securityapp.domain.DomainDevice
import com.example.securityapp.domain.StoreControlledInfoUseCase
import com.example.securityapp.modules.controlled.PhoneRepository
import com.example.securityapp.utils.Result
import com.example.securityapp.utils.Result.Error
import javax.inject.Inject

class ControlledLoginUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository,
    private val deviceRepository: DeviceRepository,
    private val storeControlledInfoUseCase: StoreControlledInfoUseCase,
    private val controlledRepository: ControlledRepository
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
            DomainDevice(
                email = email,
                password = password,
                barcodeId = id,
                phoneNumbers = sims
            )
        )
        val controllerResult = controlledRepository.upsertData(
            ControlledDeviceInControlled(
                email = email,
                numbers = sims,
                barcode = id,
                controllers = emptyList()
            )
        )
        return when (result is Result.Success && controllerResult is Result.Success) {
            true -> storeControlledInfoUseCase(email, id)
            false -> Error("Error")
        }
    }
}