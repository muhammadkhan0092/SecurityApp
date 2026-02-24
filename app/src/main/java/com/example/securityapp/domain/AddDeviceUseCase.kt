package com.example.securityapp.domain

import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.DeviceRepository
import com.example.securityapp.firebase.DtoControllerDevice
import com.example.securityapp.utils.Result
import javax.inject.Inject

class AddDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation
) {
    suspend operator fun invoke(email: String) : Result<Unit> {
        val myEmail = dataStoreRepositoryImplementation.getEmail()
        val deviceResult = deviceRepository.getDevice(myEmail)
        return when(deviceResult){
            is Result.Error<*> -> Result.Error(deviceResult.error)
            is Result.Success -> {
                val device = deviceResult.data
                when(device){
                    null -> Result.Error("Server Error")
                    else -> {
                        val connectedDevice = device.connectedDevices.toMutableList()
                        val newDevice = DtoControllerDevice(email, isBanned = false)
                        connectedDevice.add(newDevice)
                        val newDevices = device.copy(connectedDevices = connectedDevice)
                        val upsertResult = deviceRepository.upsertDevice(newDevices)
                        when(upsertResult){
                            is Result.Error<*> -> Result.Error("Error Removing Device")
                            is Result.Success<*> -> Result.Success(Unit)
                        }
                    }
                }
            }
        }
    }
}