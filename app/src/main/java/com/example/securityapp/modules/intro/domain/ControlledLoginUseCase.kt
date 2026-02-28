package com.example.securityapp.modules.intro.domain

import com.example.securityapp.core.data.repository.ControlledDeviceDto
import com.example.securityapp.modules.controlled.data.ControlledRepository
import com.example.securityapp.core.data.repository.DeviceRepository
import com.example.securityapp.core.data.repository.LoginRepository
import com.example.securityapp.domain.DomainDevice
import com.example.securityapp.modules.controlled.domain.usecase.StoreControlledInfoUseCase
import com.example.securityapp.modules.controlled.domain.PhoneRepository
import com.example.securityapp.utils.Result
import com.example.securityapp.utils.Result.Error
import javax.inject.Inject

class ControlledLoginUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository,
    private val deviceRepository: DeviceRepository,
    private val storeControlledInfoUseCase: StoreControlledInfoUseCase,
    private val controlledRepository: ControlledRepository,
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String, id: String): Result<Unit> {
        val isAirplaneOn = phoneRepository.isAirplaneModeOn()
        if (isAirplaneOn) {
            return Error("Disable Airplane Mode")
        }
//        val sims = phoneRepository.getSimNumbers()
//        if (sims.isEmpty()) {
//            return Error("Insert A Sim to Continue")
//        }
        val sims = listOf("0092","0082")
        val alreadyDevice = deviceRepository.getDevice(email)
        when (alreadyDevice) {
            is Error<*> -> return Error("Server Error")
            is Result.Success -> {
                when (alreadyDevice.data) {
                    null -> Unit
                    else -> {
                        return when (alreadyDevice.data.password == password) {
                            true -> Result.Success(Unit)
                            false -> Error("Invalid Password")
                        }
                    }
                }
            }
        }
        val result = loginRepository.insertControlledUser(
            controlledData =  ControlledDeviceDto(
                email = email,
                numbers = sims,
                barcode = id,
                controllers = emptyList()
            ),
            user = DomainDevice(
                email = email,
                password = password,
                barcodeId = id,
                phoneNumbers = sims
            )
        )
        return when (result is Result.Success) {
            true -> storeControlledInfoUseCase(email, id)
            false -> Error("Error")
        }
    }
}