package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.domain.InsertMessage
import com.example.securityapp.core.domain.MessageFromControlled
import com.example.securityapp.core.domain.MessageTypeFromControlled
import com.example.securityapp.utils.Result
import javax.inject.Inject

class FactoryReset @Inject constructor(
    private val smsCommandRepository: SmsCommandRepository,
    private val insertMessage: InsertMessage
) {
    suspend operator fun invoke(numbers: List<String>, email: String) {
        val firstNumber = numbers.firstOrNull()
        val messageFromControlled = MessageFromControlled(
            string = "Factory Reset Complete",
            type = MessageTypeFromControlled.NORMAL
        )
        insertMessage(email,"Factory Reset From $email", MessageTypeFromControlled.NORMAL)
        val serializedMessage = smsCommandRepository.serializeToString(messageFromControlled)
        when (serializedMessage) {
            is Result.Error<*> -> {
                firstNumber?.let {
                    smsCommandRepository.sendSms(it, serializedMessage.error)
                }
            }
            is Result.Success -> {
                firstNumber?.let {
                    smsCommandRepository.sendSms(it, serializedMessage.data)
                }
            }
        }
    }
}