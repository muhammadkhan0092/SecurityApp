package com.example.securityapp.modules.login.domain

import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.firebase.DtoControllerUser
import com.example.securityapp.modules.controlled.data.models.ControlledDeviceDto
import com.example.securityapp.modules.controlled.domain.models.ControlledDomainDevice
import com.example.securityapp.modules.controller.data.models.ControllerDeviceDto

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