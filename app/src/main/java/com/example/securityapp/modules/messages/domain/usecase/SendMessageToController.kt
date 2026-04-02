package com.example.securityapp.modules.messages.domain.usecase

import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.messages.data.repository.AndroidMessageSerializer
import com.example.securityapp.modules.messages.domain.models.MessageFromControlled
import javax.inject.Inject

class SendMessageToController @Inject constructor(
    private val androidSmsManagerRepository: AndroidMessageSerializer
) {
    operator fun invoke(firstNumber: String?,messageFromControlled: MessageFromControlled){
        val serializedMessage = androidSmsManagerRepository.serializeToString(messageFromControlled)
        when (serializedMessage) {
            is Result.Error<*> -> {
                firstNumber?.let {
                    androidSmsManagerRepository.sendSms(it, serializedMessage.error)
                }
            }
            is Result.Success -> {
                firstNumber?.let {
                    androidSmsManagerRepository.sendSms(it, serializedMessage.data)
                }
            }
        }
    }
}