package com.example.securityapp.modules.controller.data.mappers

import com.example.securityapp.modules.controller.data.models.ControllerMessagesEntity
import com.example.securityapp.modules.controller.domain.ControllerMessagesDomain

fun ControllerMessagesEntity.mapToControllerMessagesDomain(): ControllerMessagesDomain {
    return ControllerMessagesDomain(
        message = message,
        type = type
    )
}

fun ControllerMessagesDomain.mapToControllerMessagesEntity(email : String): ControllerMessagesEntity {
    return ControllerMessagesEntity(
        id = 0,
        email = email,
        message = message,
        type = type
    )
}