package com.example.securityapp.firebase

data class DtoDevice(
    val barcodeId : String,
    val phoneNumbers : List<String>,
    val blockedParents : List<String>
)