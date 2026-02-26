package com.example.securityapp.modules.controlled.data

import com.example.securityapp.core.data.repository.ControlledDeviceInControlled
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controller.domain.ControllerDomain

fun ControlledEntity.mapToControlledDomain(): ControlledDomain {
    return ControlledDomain(
        email = email,
        numbers = numbers
    )
}
fun ControlledDomain.mapToControlledEntity(): ControlledEntity {
    return ControlledEntity(
        email = email,
        numbers = numbers
    )
}
fun ControlledDeviceInControlled.mapToControlledDomain() : ControlledDomain {
    return ControlledDomain(
        email = email,
        numbers = numbers
    )
}