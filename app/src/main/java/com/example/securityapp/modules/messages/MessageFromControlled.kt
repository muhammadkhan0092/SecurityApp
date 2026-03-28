package com.example.securityapp.modules.messages


data class MessageFromControlled(
    val string : String,
    val type : MessageTypeFromControlled
)
enum class MessageTypeFromControlled{
    NORMAL,
    ERROR
}