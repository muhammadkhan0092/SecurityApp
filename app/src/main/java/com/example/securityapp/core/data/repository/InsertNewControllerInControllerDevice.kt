package com.example.securityapp.core.data.repository

import com.example.securityapp.utils.Result
import javax.inject.Inject

class InsertNewControllerInControllerDevice @Inject constructor(
    private val controllerRepository: ControllerRepository
) {
    suspend operator fun invoke(
        email : String,
        barcode : String,
        numbers : List<String>,
        controllerData : ControllerDeviceInController
    ): Result<Unit> {
        val list = controllerData.devices.toMutableList()
        list.add(ControlledDeviceForController(
            barcode =barcode,
            email = email,
            number = numbers
        ))
        val newData = controllerData.copy(devices =list)
        val result = controllerRepository.upsertData(newData)
        return when(result){
            is Result.Error-> Result.Error(result.error)
            is Result.Success-> Result.Success(Unit)
        }
    }
}