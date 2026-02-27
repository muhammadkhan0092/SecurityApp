package com.example.securityapp.modules.controller.data.mappers

import com.example.securityapp.core.data.repository.ControlledDeviceDto
import com.example.securityapp.core.data.repository.ControllerDeviceDto
import com.example.securityapp.core.data.repository.DevicesDto
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controller.data.dao.ControllerMessagesDao
import com.example.securityapp.modules.controller.data.models.ControllerEntity
import com.example.securityapp.modules.controller.data.models.ControllerMessagesEntity
import com.example.securityapp.modules.controller.domain.ControllerDomain
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