package com.example.securityapp.modules.messages

import com.example.securityapp.modules.controller.domain.models.MessagesDomain

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