package com.example.securityapp.modules.connection.domain

import android.util.Log
import com.example.securityapp.core.data.models.DevicesDto
import com.example.securityapp.modules.app_settings.data.AppAppSettingsRepoImpl
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.connection.data.FirebaseConnectionRepository
import com.example.securityapp.modules.controlled.data.models.ControlledDeviceDto
import com.example.securityapp.modules.controlled.data.repository.FirebaseControlledRepository
import com.example.securityapp.modules.controller.data.models.ControllerDeviceDto
import javax.inject.Inject

class InsertConnection @Inject constructor(
    private val firebaseControlledRepository: FirebaseControlledRepository,
    private val firebaseConnectionRepository: FirebaseConnectionRepository,
    private val appSettingsRepoImpl: AppAppSettingsRepoImpl
) {
    suspend operator fun invoke(barcode: String, controllerDevicesDto: List<DevicesDto>) {
        Log.d("KHAN","BARCODE IS $barcode")
        val result = firebaseControlledRepository.getDataByBarcode(barcode)
        val controllerData = ControllerDeviceDto(
            email = appSettingsRepoImpl.getEmail(),
            number = appSettingsRepoImpl.getNumber(),
            controlled = controllerDevicesDto
        )
        when (result) {
            is Result.Error<*> -> {
                Log.d("KHAN","IN FIRST ERROR")
                Result.Error(result.error)
            }
            is Result.Success -> {
                val oldControlledData = result.data
                when (oldControlledData != null) {
                    true -> {
                        val newControlledData = returnControlledData(
                            controllerData.number,
                            controllerData.email,
                            oldControlledData
                        )
                        val newControllerData = returnControllerData(controllerData,oldControlledData.email,oldControlledData.number)
                        firebaseConnectionRepository.insertControllerAndControllerData(
                            controlledData = newControlledData,
                            controllerData = newControllerData
                        )
                    }
                    false -> {
                        Log.d("KHAN","IN FALSE")
                        Result.Error("Data is null")
                    }
                }
            }
        }
    }
    fun returnControllerData(
        controllerData: ControllerDeviceDto,
        email: String,
        number: String
    ): ControllerDeviceDto {
        val controlledDevicesInControllerData = controllerData.controlled.toMutableList()
        controlledDevicesInControllerData.add(
            DevicesDto(
                email = email,
                number = number
            )
        )
        return controllerData.copy(controlled = controlledDevicesInControllerData)
    }
    fun returnControlledData(
        number: String,
        newEmail: String,
        oldControlledData: ControlledDeviceDto
    ): ControlledDeviceDto {
        val list = oldControlledData.controllers.toMutableList()
        list.add(
            DevicesDto(
                email = newEmail,
                number = number
            )
        )
       return oldControlledData.copy(controllers = list)
    }
}