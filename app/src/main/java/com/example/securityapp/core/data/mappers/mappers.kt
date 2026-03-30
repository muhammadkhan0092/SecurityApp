package com.example.securityapp.core.data.mappers

import com.example.securityapp.core.data.models.DevicesDto
import com.example.securityapp.modules.controlled.domain.models.ControlledDomain
import com.example.securityapp.modules.controller.domain.models.ControllerDomain

fun DevicesDto.mapToControlledDomain(): ControlledDomain {
    return ControlledDomain(
        email = email,
        number = number
    )
}


fun DevicesDto.mapToControllerDomain() : ControllerDomain{
    return ControllerDomain(
        email = email,
        number= number
    )
}