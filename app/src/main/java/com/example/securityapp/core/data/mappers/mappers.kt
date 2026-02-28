package com.example.securityapp.core.data.mappers

import com.example.securityapp.core.data.models.DevicesDto
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controller.domain.ControllerDomain

fun DevicesDto.mapToControlledDomain(): ControlledDomain {
    return ControlledDomain(
        email = email,
        numbers = numbers
    )
}


fun DevicesDto.mapToControllerDomain() : ControllerDomain{
    return ControllerDomain(
        email = email,
        numbers = numbers
    )
}