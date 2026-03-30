package com.example.securityapp.modules.controlled.data.models

import com.example.securityapp.core.data.models.DevicesDto

data class ControlledDeviceDto(
    val email : String = "",
    val barcode : String = "",
    val number : String= "",
    val controllers : List<DevicesDto> = emptyList()
)