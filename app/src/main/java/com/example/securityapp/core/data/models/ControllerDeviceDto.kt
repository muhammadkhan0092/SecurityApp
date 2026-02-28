package com.example.securityapp.core.data.models

data class ControllerDeviceDto(
    val email: String= "",
    val numbers : List<String> = emptyList(),
    val controlled : List<DevicesDto> = emptyList()
)