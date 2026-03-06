package com.example.securityapp.modules.intro.domain

import android.util.Log
import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.core.data.repository.SecurityLoginRepository
import com.example.securityapp.modules.controlled.domain.ControlledDomainDevice
import com.example.securityapp.modules.controlled.domain.repository.PhoneRepository
import com.example.securityapp.modules.controlled.domain.usecase.StoreControlledInfoUseCase
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.core.domain.utils.Result.Error
import javax.inject.Inject

class ControlledLoginUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository,
    private val storeControlledInfoUseCase: StoreControlledInfoUseCase,
    private val securityLoginRepository: SecurityLoginRepository
) {
    suspend operator fun invoke(email: String, password: String, id: String): Result<Unit> {
        val isAirplaneOn = phoneRepository.isAirplaneModeOn()
        if (isAirplaneOn) {
            return Error("Disable Airplane Mode")
        }
        phoneRepository.getSimNumbers()
//        if (sims.isEmpty()) {
//            return Error("Insert A Sim to Continue")
//        }
        val sims = listOf("0092","0082")
        val alreadyDevice = securityLoginRepository.getControlledUser(email)
        return when (alreadyDevice) {
            is Error<*> -> return Error("Server Error")
            is Result.Success -> {
                when (alreadyDevice.data) {
                    null -> newLogin(email,sims,id,password)
                    else -> {
                        alreadyLogin(
                            alreadyDevice,
                            password = password
                        )
                    }
                }
            }
        }
    }
    private suspend fun alreadyLogin(alreadyDevice: Result.Success<ControlledDomainDevice?>, password: String): Result<Unit> {
        Log.d("KHAN","CONTROLLED ALREADY LOGIN")
        return when (alreadyDevice.data!!.password == password) {
            true -> {
                storeControlledInfoUseCase(
                    email = alreadyDevice.data.email,
                    barcodeId = alreadyDevice.data.barcodeId
                )
                Result.Success(Unit)
            }
            false -> Error("Invalid Password")
        }
    }
    private suspend fun newLogin(email: String, sims: List<String>, id: String, password: String): Result<Unit> {
        Log.d("KHAN","CONTROLLED NEW LOGIN")
        val result = securityLoginRepository.insertControlledUser(
            controlledData =  ControlledDeviceDto(
                email = email,
                numbers = sims,
                barcode = id,
                controllers = emptyList()
            ),
            user = ControlledDomainDevice(
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