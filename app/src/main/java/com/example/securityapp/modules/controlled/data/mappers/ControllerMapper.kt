package com.example.securityapp.modules.controlled.data.mappers

import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.modules.controlled.data.models.ControlledEntity
import com.example.securityapp.modules.controlled.domain.ControlledDomain

fun ControlledEntity.mapControlledEntityControlledDomain(): ControlledDomain {
    return ControlledDomain(
        email = email,
        number = number
    )
}
fun ControlledDomain.mapToControlledEntity(): ControlledEntity {
    return ControlledEntity(
        email = email,
        number= number
    )
}
fun ControlledDeviceDto.mapControlledDtoControlledDomain() : ControlledDomain {
    return ControlledDomain(
        email = email,
        number = number
    )
}