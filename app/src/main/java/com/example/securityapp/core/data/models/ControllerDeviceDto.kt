package com.example.securityapp.core.data.models

data class ControllerDeviceDto(
    val email: String= "",
    val number : String = "",
    val controlled : List<DevicesDto> = emptyList()
)