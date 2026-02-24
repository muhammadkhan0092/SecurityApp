package com.example.securityapp.core.data.repository

import com.example.securityapp.utils.Result
import javax.inject.Inject

class InsertNewControlledInControlledDevice @Inject constructor(
    private val controlledRepository: ControlledRepository
) {
    suspend operator fun invoke(
        controllerData: ControllerDeviceInController,
        controlledData: ControlledDeviceInControlled
    ): Result<Unit> {
        val numbers = controllerData.numbers
        val newEmail = controllerData.email
        val list = controlledData.controllers.toMutableList()
        list.add(
            ControllerDeviceForControlled(
                email = newEmail,
                numbers = numbers
            )
        )
        val updatedData = controlledData.copy(controllers = list)
        val result = controlledRepository.upsertData(updatedData)
        return when (result) {
            is Result.Error<*> -> Result.Error(result.error)
            is Result.Success<*> -> Result.Success(Unit)
        }
    }
}