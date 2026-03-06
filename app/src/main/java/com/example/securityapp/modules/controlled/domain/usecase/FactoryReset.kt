package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.repository.AndroidSmsManagerRepository
import com.example.securityapp.core.domain.usecase.InsertMessage
import com.example.securityapp.core.domain.models.MessageFromControlled
import com.example.securityapp.core.domain.models.MessageTypeFromControlled
import com.example.securityapp.modules.controlled.data.repository.AndroidDeviceOwnerRepository
import com.example.securityapp.core.domain.utils.Result
import javax.inject.Inject

class FactoryReset @Inject constructor(
    private val androidSmsManagerRepository: AndroidSmsManagerRepository,
    private val androidDeviceOwnerRepository: AndroidDeviceOwnerRepository,
    private val insertMessage: InsertMessage
) {
    suspend operator fun invoke(numbers: List<String>, email: String) {
        val resetResult = androidDeviceOwnerRepository.resetPhone()
        val firstNumber = numbers.firstOrNull()
        when(resetResult){
            is Result.Error<*> -> {
                insertMessage(email,"Factory Reset From $email Failed", MessageTypeFromControlled.ERROR)
                val messageFromControlled = MessageFromControlled(
                    string = "Factory Reset Failed",
                    type = MessageTypeFromControlled.ERROR
                )
                insertMessage(email,"Factory Reset From $email", MessageTypeFromControlled.NORMAL)
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
            is Result.Success -> {
                val messageFromControlled = MessageFromControlled(
                    string = "Factory Reset Complete",
                    type = MessageTypeFromControlled.NORMAL
                )
                insertMessage(email,"Factory Reset From $email", MessageTypeFromControlled.NORMAL)
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
    }
}