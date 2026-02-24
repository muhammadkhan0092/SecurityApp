package com.example.securityapp.core.data.repository

import com.example.securityapp.utils.Result
import javax.inject.Inject

class InsertNewDevice @Inject constructor(
    private val controlledRepository: ControlledRepository,
    private val insertNewControlledInControlledDevice: InsertNewControlledInControlledDevice,
    private val insertNewControllerInControllerDevice: InsertNewControllerInControllerDevice
) {
    suspend operator fun invoke(barcode : String,controllerData: ControllerDeviceInController){
        val result = controlledRepository.getDataByBarcode(barcode)
        when(result){
            is Result.Error<*> ->Result.Error(result.error)
            is Result.Success -> {
                val data= result.data
                when(data!=null){
                    true -> {
                        val resultOne = insertNewControllerInControllerDevice(
                            email = data.email,
                            barcode = data.barcode,
                            numbers = data.numbers,
                            controllerData = controllerData
                        )
                        val resultTwo = insertNewControlledInControlledDevice(
                            controllerData = controllerData,
                            controlledData = data
                        )
                        when(resultOne is Result.Success && resultTwo is Result.Success){
                            true -> Result.Success(Unit)
                            false -> Result.Error("Error Adding Device")
                        }
                    }
                    false -> Result.Error("Data is null")
                }
            }
        }
    }
}