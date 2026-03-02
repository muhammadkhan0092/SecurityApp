package com.example.securityapp.core.data.models

data class BothDeviceDto(
    val controllerDto : ControllerDeviceDto = ControllerDeviceDto(),
    val controlledDto : ControlledDeviceDto = ControlledDeviceDto()
)
