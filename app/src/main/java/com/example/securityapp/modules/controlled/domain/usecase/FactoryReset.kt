package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.domain.InsertMessage
import com.example.securityapp.core.domain.MessageFromControlled
import com.example.securityapp.core.domain.MessageTypeFromControlled
import com.example.securityapp.modules.controlled.data.repository.DeviceOwnerRepository
import com.example.securityapp.utils.Result
import javax.inject.Inject

class FactoryReset @Inject constructor(
    private val smsCommandRepository: SmsCommandRepository,
    private val deviceOwnerRepository: DeviceOwnerRepository,
    private val insertMessage: InsertMessage
) {
    suspend operator fun invoke(numbers: List<String>, email: String) {
        val resetResult = deviceOwnerRepository.resetPhone()
        val firstNumber = numbers.firstOrNull()
        when(resetResult){
            is Result.Error<*> -> {
                insertMessage(email,"Factory Reset From $email Failed", MessageTypeFromControlled.ERROR)
                val messageFromControlled = MessageFromControlled(
                    string = "Factory Reset Failed",
                    type = MessageTypeFromControlled.ERROR
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
            is Result.Success -> {
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
    }
}