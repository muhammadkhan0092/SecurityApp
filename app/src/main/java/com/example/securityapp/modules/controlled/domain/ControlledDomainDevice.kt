package com.example.securityapp.modules.controlled.domain

data class ControlledDomainDevice(
    val email : String,
    val password : String,
    val barcodeId : String,
    val phoneNumbers : List<String>
)