package com.example.securityapp.domain.usecase

import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.core.data.models.ControllerDeviceDto
import com.example.securityapp.core.data.repository.ConnectionRepository
import com.example.securityapp.core.data.repository.DataStoreRepositoryImplementation
import com.example.securityapp.modules.controlled.data.repository.ControlledRepository
import com.example.securityapp.utils.Result
import javax.inject.Inject

class RemoveConnection @Inject constructor(
    private val controlledRepository: ControlledRepository,
    private val connectionRepository: ConnectionRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation
) {
    suspend operator fun invoke(controllerEmail: String): Result<Unit> {
        val controlledEmail = dataStoreRepositoryImplementation.getEmail()
        val bothDeviceResult = connectionRepository.getControllerAndControlledData(controlledEmail =controlledEmail, controllerEmail = controllerEmail)
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
                connectionRepository.insertControllerAndControllerData(
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