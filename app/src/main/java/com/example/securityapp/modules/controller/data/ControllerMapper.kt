package com.example.securityapp.modules.controller.data

import com.example.securityapp.core.data.repository.ControllerDeviceInController
import com.example.securityapp.modules.controlled.data.ControlledEntity
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controller.domain.ControllerDomain

fun ControllerEntity.mapToDomainController(): ControllerDomain {
    return ControllerDomain(
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
fun ControllerDeviceInController.mapToDomainController(): List<ControllerDomain> {
    return devices.map {device->
        ControllerDomain(
            email = device.email,
            numbers = device.number
        )
    }
}