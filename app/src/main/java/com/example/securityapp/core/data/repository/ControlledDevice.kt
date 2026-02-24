package com.example.securityapp.core.data.repository

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
