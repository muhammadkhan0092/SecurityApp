package com.example.securityapp.core.domain.models


data class MessageFromControlled(
    val string : String,
    val type : MessageTypeFromControlled
)
enum class MessageTypeFromControlled{
    NORMAL,
    ERROR
}