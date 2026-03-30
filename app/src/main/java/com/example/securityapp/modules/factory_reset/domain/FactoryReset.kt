package com.example.securityapp.modules.factory_reset.domain

import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.device_owner.data.AndroidDeviceOwnerRepository
import com.example.securityapp.modules.messages.data.AndroidSmsManagerRepository
import com.example.securityapp.modules.messages.domain.InsertMessage
import com.example.securityapp.modules.messages.domain.MessageFromControlled
import com.example.securityapp.modules.messages.domain.MessageTypeFromControlled
import javax.inject.Inject

class FactoryReset @Inject constructor(
    private val androidSmsManagerRepository: AndroidSmsManagerRepository,
    private val androidDeviceOwnerRepository: AndroidDeviceOwnerRepository,
    private val insertMessage: InsertMessage
) {
    suspend operator fun invoke(firstNumber: String, email: String) {
        val resetResult = androidDeviceOwnerRepository.resetPhone()
        when (resetResult) {
            is Result.Error<*> -> {
                insertMessage(
                    email,
                    "Request : Factory Reset From $email\nStatus : Failed\nReason : App Not Device Owner",
                    MessageTypeFromControlled.ERROR
                )
                val messageFromControlled = MessageFromControlled(
                    string = "Request : Factory Reset\nStatus : Failed\nReason : App Not Device Owner",
                    type = MessageTypeFromControlled.ERROR
                )
                val serializedMessage = androidSmsManagerRepository.serializeToString(messageFromControlled)
                when (serializedMessage) {
                    is Result.Error<*> -> {
                        androidSmsManagerRepository.sendSms(firstNumber, serializedMessage.error)
                    }

                    is Result.Success -> {
                        androidSmsManagerRepository.sendSms(firstNumber, serializedMessage.data)
                    }
                }
            }

            is Result.Success -> {
                val messageFromControlled = MessageFromControlled(
                    string = "Request : Factory Reset\nStatus : Success",
                    type = MessageTypeFromControlled.NORMAL
                )
                insertMessage(email, "Request : Factory Reset From $email\nStatus : Success", MessageTypeFromControlled.NORMAL)
                val serializedMessage =
                    androidSmsManagerRepository.serializeToString(messageFromControlled)
                when (serializedMessage) {
                    is Result.Error<*> -> {
                        androidSmsManagerRepository.sendSms(firstNumber, serializedMessage.error)
                    }

                    is Result.Success -> {
                        androidSmsManagerRepository.sendSms(firstNumber, serializedMessage.data)
                    }
                }
            }
        }
    }
}