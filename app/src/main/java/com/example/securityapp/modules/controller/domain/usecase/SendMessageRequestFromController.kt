package com.example.securityapp.modules.controller.domain.usecase

import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.domain.MessageFromController
import com.example.securityapp.core.domain.MessageTypeFromControlled
import com.example.securityapp.modules.controller.data.repository.ControllerMessagesRepository
import com.example.securityapp.modules.controller.domain.ControllerMessagesDomain
import com.example.securityapp.utils.Result
import javax.inject.Inject

class SendMessageRequestFromController @Inject constructor(
    private val smsRepository: SmsCommandRepository,
    private val messagesRepository: ControllerMessagesRepository
) {
    suspend operator fun invoke(phoneNo : String,data : MessageFromController,email : String) : Result<Unit>{
        val logMessage = when(data){
            MessageFromController.BLOCK_APPS -> "App Block Requested"
            MessageFromController.WIPE_GALLERY -> "Wipe Gallery Requested"
            MessageFromController.GET_LOCATION -> "Get Location Requested"
            MessageFromController.FACTORY_RESET -> "Factory Reset Requested"
        }
        val messageStrResult = smsRepository.serializeToString(data)
        return when(messageStrResult){
            is Result.Error<*> -> Result.Error(messageStrResult.error)
            is Result.Success -> {
                smsRepository.sendSms(phoneNo,messageStrResult.data)
                messagesRepository.upsertData(
                    ControllerMessagesDomain(logMessage, MessageTypeFromControlled.NORMAL),
                    email = email
                )
                Result.Success(Unit)
            }
        }
    }
}