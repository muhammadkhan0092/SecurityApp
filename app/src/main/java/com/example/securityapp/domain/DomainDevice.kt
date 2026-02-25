package com.example.securityapp.domain

import com.example.securityapp.firebase.DtoControllerDevice

data class DomainDevice(
    val email : String,
    val password : String,
    val barcodeId : String,
    val phoneNumbers : List<String>
)
