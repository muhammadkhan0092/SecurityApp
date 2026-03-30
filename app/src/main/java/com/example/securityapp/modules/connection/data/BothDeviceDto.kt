package com.example.securityapp.modules.connection.data

import com.example.securityapp.modules.controlled.data.models.ControlledDeviceDto
import com.example.securityapp.modules.controller.data.models.ControllerDeviceDto

data class BothDeviceDto(
    val controllerDto : ControllerDeviceDto = ControllerDeviceDto(),
    val controlledDto : ControlledDeviceDto = ControlledDeviceDto()
)