package com.example.securityapp.core.data

import com.example.securityapp.domain.DomainDevice
import com.example.securityapp.firebase.DtoDevice

fun DtoDevice.toDomainDevice(): DomainDevice {
    return DomainDevice(
        email = email,
        password = password,
        barcodeId = barcodeId,
        phoneNumbers = phoneNumbers
    )
}
fun DomainDevice.toDtoDevice(): DtoDevice {
    return DtoDevice(
        email = email,
        password = password,
        barcodeId = barcodeId,
        phoneNumbers = phoneNumbers
    )
}