package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.domain.MessageFromControlled
import com.example.securityapp.utils.Result
import javax.inject.Inject

class SendMessageToController @Inject constructor(
    private val smsCommandRepository: SmsCommandRepository
) {
    operator fun invoke(firstNumber: String?,messageFromControlled: MessageFromControlled){
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
