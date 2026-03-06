package com.example.securityapp.core.domain.repository

import com.example.securityapp.core.data.models.BothDeviceDto
import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.core.data.models.ControllerDeviceDto
import com.example.securityapp.core.domain.utils.Result

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