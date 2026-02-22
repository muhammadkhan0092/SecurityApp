package com.example.securityapp.firebase

data class DtoDevice(
    val email : String,
    val barcodeId : String,
    val phoneNumbers : List<String>,
    val connectedDevices : List<DtoControllerDevice>
)