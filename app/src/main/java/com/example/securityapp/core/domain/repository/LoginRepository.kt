package com.example.securityapp.core.domain.repository

import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.core.data.models.ControllerDeviceDto
import com.example.securityapp.firebase.DtoControllerUser
import com.example.securityapp.modules.controlled.domain.ControlledDomainDevice
import com.example.securityapp.core.domain.utils.Result

interface LoginRepository {
    suspend fun insertControlledUser(
        controlledData: ControlledDeviceDto,
        user: ControlledDomainDevice
    ): Result<Unit>

    suspend fun insertControllerUser(
        controllerData: ControllerDeviceDto,
        user: DtoControllerUser
    ): Result<Unit>

    suspend fun getControllerUser(email: String): Result<DtoControllerUser?>
    suspend fun getControlledUser(email: String): Result<ControlledDomainDevice?>
}