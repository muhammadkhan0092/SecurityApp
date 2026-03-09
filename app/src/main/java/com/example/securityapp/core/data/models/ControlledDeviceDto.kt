package com.example.securityapp.core.data.models

data class ControlledDeviceDto(
    val email : String = "",
    val barcode : String = "",
    val number : String= "",
    val controllers : List<DevicesDto> = emptyList()
)