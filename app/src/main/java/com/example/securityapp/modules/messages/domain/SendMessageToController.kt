package com.example.securityapp.modules.messages.domain

import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.messages.data.AndroidSmsManagerRepository
import javax.inject.Inject

class SendMessageToController @Inject constructor(
    private val androidSmsManagerRepository: AndroidSmsManagerRepository
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