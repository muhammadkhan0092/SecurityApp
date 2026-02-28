package com.example.securityapp.modules.controlled.data

import com.example.securityapp.core.data.repository.ControlledDeviceDto
import com.example.securityapp.modules.controlled.data.models.ControlledEntity
import com.example.securityapp.modules.controlled.domain.ControlledDomain

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
fun ControlledDeviceDto.mapToControlledDomain() : ControlledDomain {
    return ControlledDomain(
        email = email,
        numbers = numbers
    )
}