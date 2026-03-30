package com.example.securityapp.modules.controller.domain.models

import com.example.securityapp.modules.messages.domain.MessageTypeFromControlled

data class MessagesDomain(
    val message : String = "",
    val type : MessageTypeFromControlled = MessageTypeFromControlled.NORMAL
)
