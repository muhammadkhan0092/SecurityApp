package com.example.securityapp.modules.connection.domain

import com.example.securityapp.modules.controlled.data.models.ControlledDeviceDto
import com.example.securityapp.modules.controller.data.models.ControllerDeviceDto
import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.modules.connection.data.FirebaseConnectionRepository
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controlled.data.repository.FirebaseControlledRepository
import javax.inject.Inject

class RemoveConnection @Inject constructor(
    private val firebaseControlledRepository: FirebaseControlledRepository,
    private val firebaseConnectionRepository: FirebaseConnectionRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(controllerEmail: String): Result<Unit> {
        val controlledEmail = dataStoreRepository.getEmail()
        val bothDeviceResult = firebaseConnectionRepository.getControllerAndControlledData(controlledEmail =controlledEmail, controllerEmail = controllerEmail)
        return when(bothDeviceResult){
            is Result.Error<*> -> Result.Error(bothDeviceResult.error)
            is Result.Success->{
                val bothData = bothDeviceResult.data
                val newControllerData = returnControllerData(
                    controllerData = bothData.controllerDto,
                    controlledEmail = controlledEmail
                )
                val newControlledData = returnControlledData(
                    oldControlledData = bothData.controlledDto,
                    controllerEmail = controllerEmail
                )
                firebaseConnectionRepository.insertControllerAndControllerData(
                    controllerData = newControllerData,
                    controlledData = newControlledData
                )
            }
        }
    }
    fun returnControllerData(
        controllerData: ControllerDeviceDto,
        controlledEmail: String
    ): ControllerDeviceDto {
        val controlledDevicesInControllerData = controllerData.controlled.toMutableList()
        controlledDevicesInControllerData.removeIf {
            controlledEmail==it.email
        }
        return controllerData.copy(controlled = controlledDevicesInControllerData)
    }
    fun returnControlledData(
        oldControlledData: ControlledDeviceDto,
        controllerEmail: String
    ): ControlledDeviceDto {
        val list = oldControlledData.controllers.toMutableList()
        list.removeIf {
            it.email==controllerEmail
        }
       return oldControlledData.copy(controllers = list)
    }
}