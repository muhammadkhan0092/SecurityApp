package com.example.securityapp.core.data.mappers

import com.example.securityapp.core.data.models.MessagesEntity
import com.example.securityapp.modules.controller.domain.MessagesDomain

fun MessagesEntity.mapToMessagesDomain(): MessagesDomain {
    return MessagesDomain(
        message = message,
        type = type
    )
}

fun MessagesDomain.mapToMessagesEntity(email : String): MessagesEntity {
    return MessagesEntity(
        id = 0,
        email = email,
        message = message,
        type = type
    )
}