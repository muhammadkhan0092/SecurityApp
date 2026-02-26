package com.example.securityapp.domain.usecase

import android.util.Log
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.ConnectionRepository
import com.example.securityapp.modules.controlled.data.ControlledRepository
import com.example.securityapp.core.data.repository.ControllerDeviceDto
import com.example.securityapp.core.data.repository.DevicesDto
import com.example.securityapp.modules.controlled.presentation.PhoneRepository
import com.example.securityapp.utils.Result
import javax.inject.Inject

class InsertConnection @Inject constructor(
    private val controlledRepository: ControlledRepository,
    private val connectionRepository: ConnectionRepository,
    private val phoneRepository: PhoneRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation
) {
    suspend operator fun invoke(barcode: String, controllerDevicesDto: List<DevicesDto>) {
        val result = controlledRepository.getDataByBarcode(barcode)
        val controllerData = ControllerDeviceDto(
            email = dataStoreRepositoryImplementation.getEmail(),
            numbers = phoneRepository.getSimNumbers(),
            controlled = controllerDevicesDto
        )
        Log.d("KHAN","OLD CONTROLLER DATA IS $controllerData")
        when (result) {
            is Result.Error<*> -> Result.Error(result.error)
            is Result.Success -> {
                val oldControlledData = result.data
                Log.d("KHAN","OLD CONTROLLED DATA IS $oldControlledData")
                when (oldControlledData != null) {
                    true -> {
                        val numbers = controllerData.numbers
                        val newEmail = controllerData.email
                        val list = oldControlledData.controllers.toMutableList()
                        list.add(
                            DevicesDto(
                                email = newEmail,
                                numbers = numbers
                            )
                        )
                        val newControlledData = oldControlledData.copy(controllers = list)
                        val controlledDevicesInControllerData = controllerData.controlled.toMutableList()
                        controlledDevicesInControllerData.add(
                            DevicesDto(
                                email = oldControlledData.email,
                                numbers = oldControlledData.numbers
                            )
                        )
                        val newControllerData = controllerData.copy(controlled = controlledDevicesInControllerData)
                        Log.d("KHAN","NEW CONTROLLED DATA $newControlledData")
                        Log.d("KHAN","NEW CONTROLLER DATA $newControllerData")
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