package com.example.securityapp.modules.controlled.domain.models

data class ControlledDomainDevice(
    val email : String = "",
    val password : String = "",
    val barcodeId : String = "",
    val phoneNumber : String = ""
)