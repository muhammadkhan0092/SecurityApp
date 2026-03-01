package com.example.securityapp.modules.controller.domain.models

import com.example.securityapp.core.domain.MessageTypeFromControlled

data class MessagesDomain(
    val message : String = "",
    val type : MessageTypeFromControlled = MessageTypeFromControlled.NORMAL
)
