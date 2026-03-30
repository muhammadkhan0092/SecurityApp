package com.example.securityapp.modules.controller.data.models

import com.example.securityapp.core.data.models.DevicesDto

data class ControllerDeviceDto(
    val email: String= "",
    val number : String = "",
    val controlled : List<DevicesDto> = emptyList()
)