package com.example.securityapp.core.data.repository

import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controller.domain.ControllerDomain

data class ControlledDeviceDto(
    val email : String = "",
    val barcode : String = "",
    val numbers : List<String> = emptyList(),
    val controllers : List<DevicesDto> = emptyList()
)
fun DevicesDto.mapToControlledDomain(): ControlledDomain {
    return ControlledDomain(
        email = email,
        numbers = numbers
    )
}


fun DevicesDto.mapToControllerDomain() : ControllerDomain{
    return ControllerDomain(
        email = email,
        numbers = numbers
    )
}
data class DevicesDto(
    val email : String = "",
    val numbers : List<String> = emptyList()
)

data class ControllerDeviceDto(
    val email: String= "",
    val numbers : List<String> = emptyList(),
    val controlled : List<DevicesDto> = emptyList()
)
