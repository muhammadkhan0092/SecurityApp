package com.example.securityapp.modules.messages.domain.models

data class MessagesDomain(
    val message : String = "",
    val type : MessageTypeFromControlled = MessageTypeFromControlled.NORMAL
)