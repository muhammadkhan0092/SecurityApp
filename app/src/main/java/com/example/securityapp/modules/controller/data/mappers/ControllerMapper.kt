package com.example.securityapp.modules.controller.data.mappers

import com.example.securityapp.modules.controlled.data.models.ControlledDeviceDto
import com.example.securityapp.modules.controller.data.models.ControllerDeviceDto
import com.example.securityapp.core.data.models.DevicesDto
import com.example.securityapp.modules.controlled.domain.models.ControlledDomain
import com.example.securityapp.modules.controller.data.models.ControllerEntity
import com.example.securityapp.modules.controller.domain.models.ControllerDomain

fun ControllerEntity.mapToDomainController(): ControllerDomain {
    return ControllerDomain(
        email = email,
        number = number
    )
}
fun ControllerDomain.mapToDevicesDto(): DevicesDto {
    return DevicesDto(
        email = email,
        number = number
    )
}
fun ControlledDomain.mapToDevicesDto(): DevicesDto {
    return DevicesDto(
        email = email,
        number = number
    )
}
fun ControllerDomain.mapToControllerEntity(): ControllerEntity {
    return ControllerEntity(
        email = email,
        number = number
    )
}
fun ControllerDomain.mapToControlledDto(barcode : String): ControlledDeviceDto {
    return ControlledDeviceDto(
        email = email,
        barcode = barcode,
        number = number
    )
}
fun ControllerDeviceDto.mapToDomainController(): List<ControllerDomain> {
    return controlled.map {device->
        ControllerDomain(
            email = device.email,
            number = device.number
        )
    }
}