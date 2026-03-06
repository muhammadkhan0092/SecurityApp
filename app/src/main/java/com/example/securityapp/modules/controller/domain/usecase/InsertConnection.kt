package com.example.securityapp.modules.controller.domain.usecase

import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.core.data.models.ControllerDeviceDto
import com.example.securityapp.core.data.models.DevicesDto
import com.example.securityapp.core.data.repository.FirebaseConnectionRepository
import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.modules.controlled.data.repository.FirebaseControlledRepository
import com.example.securityapp.modules.controlled.domain.repository.PhoneRepository
import com.example.securityapp.core.domain.utils.Result
import javax.inject.Inject

class InsertConnection @Inject constructor(
    private val firebaseControlledRepository: FirebaseControlledRepository,
    private val firebaseConnectionRepository: FirebaseConnectionRepository,
    private val phoneRepository: PhoneRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(barcode: String, controllerDevicesDto: List<DevicesDto>) {
        val result = firebaseControlledRepository.getDataByBarcode(barcode)
        val controllerData = ControllerDeviceDto(
            email = dataStoreRepository.getEmail(),
            numbers = phoneRepository.getSimNumbers(),
            controlled = controllerDevicesDto
        )
        when (result) {
            is Result.Error<*> -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                val oldControlledData = result.data
                when (oldControlledData != null) {
                    true -> {
                        val newControlledData = returnControlledData(
                            controllerData.numbers,
                            controllerData.email,
                            oldControlledData
                        )
                        val newControllerData = returnControllerData(controllerData,oldControlledData.email,oldControlledData.numbers)
                        firebaseConnectionRepository.insertControllerAndControllerData(
                            controlledData = newControlledData,
                            controllerData = newControllerData
                        )
                    }

                    false -> {
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