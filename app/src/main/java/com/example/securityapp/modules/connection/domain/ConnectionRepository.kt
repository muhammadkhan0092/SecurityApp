package com.example.securityapp.modules.connection.domain

import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.connection.data.BothDeviceDto
import com.example.securityapp.modules.controlled.data.models.ControlledDeviceDto
import com.example.securityapp.modules.controller.data.models.ControllerDeviceDto

interface ConnectionRepository{
    suspend fun insertControllerAndControllerData(
        controllerData : ControllerDeviceDto,
        controlledData : ControlledDeviceDto
    ): Result<Unit>
    suspend fun getControllerAndControlledData(
        controllerEmail: String,
        controlledEmail: String
    ): Result<BothDeviceDto>
}