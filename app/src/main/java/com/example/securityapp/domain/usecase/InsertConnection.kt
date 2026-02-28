package com.example.securityapp.domain.usecase

import android.util.Log
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.ConnectionRepository
import com.example.securityapp.core.data.repository.ControlledDeviceDto
import com.example.securityapp.modules.controlled.data.ControlledRepository
import com.example.securityapp.core.data.repository.ControllerDeviceDto
import com.example.securityapp.core.data.repository.DevicesDto
import com.example.securityapp.modules.controlled.domain.PhoneRepository
import com.example.securityapp.utils.Result
import javax.inject.Inject

class InsertConnection @Inject constructor(
    private val controlledRepository: ControlledRepository,
    private val connectionRepository: ConnectionRepository,
    private val phoneRepository: PhoneRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation
) {
    suspend operator fun invoke(barcode: String, controllerDevicesDto: List<DevicesDto>) {
        Log.d("KHAN","BARCODE IS $barcode")
        val result = controlledRepository.getDataByBarcode(barcode)
        val controllerData = ControllerDeviceDto(
            email = dataStoreRepositoryImplementation.getEmail(),
            numbers = phoneRepository.getSimNumbers(),
            controlled = controllerDevicesDto
        )
        Log.d("KHAN","OLD CONTROLLER DATA IS $controllerData")
        when (result) {
            is Result.Error<*> -> {
                Log.d("KHAN","ERROR IS ${result.error}")
                Result.Error(result.error)
            }
            is Result.Success -> {
                val oldControlledData = result.data
                Log.d("KHAN","OLD CONTROLLED DATA IS $oldControlledData")
                when (oldControlledData != null) {
                    true -> {
                        val newControlledData = returnControlledData(
                            controllerData.numbers,
                            controllerData.email,
                            oldControlledData
                        )
                        val newControllerData = returnControllerData(controllerData,oldControlledData.email,oldControlledData.numbers)
                        Log.d("KHAN","NEW CONTROLLED DATA $newControlledData")
                        Log.d("KHAN","NEW CONTROLLER DATA $newControllerData")
                        connectionRepository.insertControllerAndControllerData(
                            controlledData = newControlledData,
                            controllerData = newControllerData
                        )
                    }

                    false -> {
                        Log.d("KHAN","DATA IS NULL")
                        Result.Error("Data is null")
                    }
                }
            }
        }
    }
    fun returnControllerData(
        controllerData: ControllerDeviceDto,
        email: String,
        numbers: List<String>
    ): ControllerDeviceDto {
        val controlledDevicesInControllerData = controllerData.controlled.toMutableList()
        controlledDevicesInControllerData.add(
            DevicesDto(
                email = email,
                numbers = numbers
            )
        )
        return controllerData.copy(controlled = controlledDevicesInControllerData)
    }
    fun returnControlledData(
        numbers: List<String>,
        newEmail: String,
        oldControlledData: ControlledDeviceDto
    ): ControlledDeviceDto {
        val list = oldControlledData.controllers.toMutableList()
        list.add(
            DevicesDto(
                email = newEmail,
                numbers = numbers
            )
        )
       return oldControlledData.copy(controllers = list)
    }
}