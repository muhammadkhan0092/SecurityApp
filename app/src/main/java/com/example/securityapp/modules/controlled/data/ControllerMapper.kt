package com.example.securityapp.modules.controlled.data

import com.example.securityapp.modules.controlled.domain.ControlledDomain

fun ControlledEntity.mapToControllerDomain(): ControlledDomain {
    return ControlledDomain(
        email = email,
        numbers = numbers
    )
}