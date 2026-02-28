package com.example.securityapp.core.data.models

data class ControlledDeviceDto(
    val email : String = "",
    val barcode : String = "",
    val numbers : List<String> = emptyList(),
    val controllers : List<DevicesDto> = emptyList()
)