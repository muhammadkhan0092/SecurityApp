package com.example.securityapp.modules.login.domain

import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.core.domain.utils.Result.Error
import com.example.securityapp.modules.app_settings.domain.StoreAppSettingsForControlled
import com.example.securityapp.modules.controlled.data.models.ControlledDeviceDto
import com.example.securityapp.modules.controlled.domain.models.ControlledDomainDevice
import com.example.securityapp.modules.login.data.SecurityLoginRepository
import javax.inject.Inject

class ControlledLoginUseCase @Inject constructor(
    private val storeAppSettingsForControlled: StoreAppSettingsForControlled,
    private val securityLoginRepository: SecurityLoginRepository
) {
    suspend operator fun invoke(email: String, password: String, id: String,number : String): Result<Unit> {
        val alreadyDevice = securityLoginRepository.getControlledUser(email)
        return when (alreadyDevice) {
            is Error<*> -> {
                return Error("Server Error")
            }
            is Result.Success -> {
                when (alreadyDevice.data) {
                    null -> newLogin(email,number,id,password)
                    else -> {
                        alreadyLogin(
                            alreadyDevice,
                            password = password,
                            alreadyDevice.data.phoneNumber
                        )
                    }
                }
            }
        }
    }
    private suspend fun alreadyLogin(alreadyDevice: Result.Success<ControlledDomainDevice?>, password: String,number: String): Result<Unit> {
        return when (alreadyDevice.data!!.password == password) {
            true -> {
                storeAppSettingsForControlled(
                    email = alreadyDevice.data.email,
                    barcodeId = alreadyDevice.data.barcodeId,
                    number
                )
                Result.Success(Unit)
            }
            false -> Error("Invalid Password")
        }
    }
    private suspend fun newLogin(email: String, sims: String, id: String, password: String): Result<Unit> {
        val result = securityLoginRepository.insertControlledUser(
            controlledData =  ControlledDeviceDto(
                email = email,
                number = sims,
                barcode = id,
                controllers = emptyList()
            ),
            user = ControlledDomainDevice(
                email = email,
                password = password,
                barcodeId = id,
                phoneNumber = sims
            )
        )
        return when (result is Result.Success) {
            true -> storeAppSettingsForControlled(email, id, sims)
            false -> Error("Error")
        }
    }
}