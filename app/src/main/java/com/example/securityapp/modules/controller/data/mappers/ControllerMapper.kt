package com.example.securityapp.modules.controller.data.mappers

import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.core.data.models.ControllerDeviceDto
import com.example.securityapp.core.data.models.DevicesDto
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controller.data.models.ControllerEntity
import com.example.securityapp.modules.controller.domain.ControllerDomain

fun ControllerEntity.mapToDomainController(): ControllerDomain {
    return ControllerDomain(
        email = email,
        numbers = numbers
    )
}
fun ControllerDomain.mapToDevicesDto(): DevicesDto {
    return DevicesDto(
        email = email,
        numbers = numbers
    )
}
fun ControlledDomain.mapToDevicesDto(): DevicesDto {
    return DevicesDto(
        email = email,
        numbers = numbers
    )
}
fun ControllerDomain.mapToControllerEntity(): ControllerEntity {
    return ControllerEntity(
        email = email,
        numbers = numbers
    )
}
fun ControllerDomain.mapToControlledDto(barcode : String): ControlledDeviceDto {
    return ControlledDeviceDto(
        email = email,
        barcode = barcode,
        numbers = numbers
    )
}
fun ControllerDeviceDto.mapToDomainController(): List<ControllerDomain> {
    return controlled.map {device->
        ControllerDomain(
            email = device.email,
            numbers = device.numbers
        )
    }
}