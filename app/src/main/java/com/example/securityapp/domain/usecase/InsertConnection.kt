package com.example.securityapp.domain.usecase

import com.example.securityapp.core.data.repository.ConnectionRepository
import com.example.securityapp.core.data.repository.ControlledDeviceForController
import com.example.securityapp.core.data.controlled.ControlledRepository
import com.example.securityapp.core.data.repository.ControllerDeviceForControlled
import com.example.securityapp.core.data.repository.ControllerDeviceInController
import com.example.securityapp.utils.Result
import javax.inject.Inject

class InsertConnection @Inject constructor(
    private val controlledRepository: ControlledRepository,
    private val connectionRepository: ConnectionRepository
) {
    suspend operator fun invoke(barcode: String, controllerData: ControllerDeviceInController) {
        val result = controlledRepository.getDataByBarcode(barcode)
        when (result) {
            is Result.Error<*> -> Result.Error(result.error)
            is Result.Success -> {
                val oldControlledData = result.data
                when (oldControlledData != null) {
                    true -> {
                        val numbers = controllerData.numbers
                        val newEmail = controllerData.email
                        val list = oldControlledData.controllers.toMutableList()
                        list.add(
                            ControllerDeviceForControlled(
                                email = newEmail,
                                numbers = numbers
                            )
                        )
                        val newControlledData = oldControlledData.copy(controllers = list)
                        val controlledDevicesInControlledData = controllerData.devices.toMutableList()
                        controlledDevicesInControlledData.add(
                            ControlledDeviceForController(
                                barcode = barcode,
                                email = oldControlledData.email,
                                number = numbers
                            )
                        )
                        val newControllerData = controllerData.copy(devices = controlledDevicesInControlledData)
                        connectionRepository.insertControllerAndControllerData(
                            controlledData = newControlledData,
                            controllerData = newControllerData
                        )
                    }

                    false -> Result.Error("Data is null")
                }
            }
        }
    }
}