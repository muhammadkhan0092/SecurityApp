package com.example.securityapp.core.domain


data class MessageFromControlled(
    val string : String,
    val type : MessageTypeFromControlled
)
enum class MessageTypeFromControlled{
    NORMAL,
    ERROR
}