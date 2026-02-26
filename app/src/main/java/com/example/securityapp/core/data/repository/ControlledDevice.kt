package com.example.securityapp.core.data.repository

import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controller.domain.ControllerDomain

data class ControlledDeviceInControlled(
    val email : String,
    val barcode : String,
    val numbers : List<String>,
    val controllers : List<ControllerDeviceForControlled>
)
data class ControllerDeviceForControlled(
    val email : String,
    val numbers : List<String>
)
fun ControllerDeviceForControlled.mapToControlledDomain(): ControlledDomain {
    return ControlledDomain(
        email = email,
        numbers = numbers
    )
}


fun ControlledDeviceForController.mapToControllerDomain() : ControllerDomain{
    return ControllerDomain(
        email = email,
        numbers = number
    )
}
data class ControllerDeviceInController(
    val email: String,
    val numbers : List<String>,
    val devices : List<ControlledDeviceForController>
)
data class ControlledDeviceForController(
    val barcode : String,
    val email : String,
    val number : List<String>
)
