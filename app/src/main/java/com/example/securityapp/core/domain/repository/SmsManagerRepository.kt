package com.example.securityapp.core.domain.repository

import com.example.securityapp.core.domain.models.MessageFromControlled
import com.example.securityapp.core.domain.models.MessageFromController
import com.example.securityapp.core.domain.utils.Result

interface SmsManagerRepository{
    fun sendSms(phone: String, message: String)
    fun serializeToString(data : MessageFromController): Result<String>
    fun serializeToString(data : MessageFromControlled): Result<String>
    fun deserializeToMessageFromController(data : String) : Result<MessageFromController>
    fun deserializeToMessageFromControlled(data : String) : Result<MessageFromControlled>
}