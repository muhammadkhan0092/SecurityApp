package com.example.securityapp.modules.controller.domain.usecase

import com.example.securityapp.core.data.repository.AndroidSmsManagerRepository
import com.example.securityapp.core.domain.models.MessageFromController
import com.example.securityapp.core.domain.models.MessageTypeFromControlled
import com.example.securityapp.core.data.repository.RoomMessagesRepository
import com.example.securityapp.modules.controller.domain.models.MessagesDomain
import com.example.securityapp.core.domain.utils.Result
import javax.inject.Inject

class SendMessageRequestFromController @Inject constructor(
    private val smsRepository: AndroidSmsManagerRepository,
    private val messagesRepository: RoomMessagesRepository
) {
    suspend operator fun invoke(phoneNo : String,data : MessageFromController,email : String) : Result<Unit>{
        val logMessage = when(data){
            MessageFromController.BLOCK_APPS -> "App Block Requested"
            MessageFromController.WIPE_GALLERY -> "Wipe Gallery Requested"
            MessageFromController.GET_LOCATION -> "Get Location Requested"
            MessageFromController.FACTORY_RESET -> "Factory Reset Requested"
            MessageFromController.UNINSTALL_APPS -> "Uninstall Requested"
        }
        val messageStrResult = smsRepository.serializeToString(data)
        return when(messageStrResult){
            is Result.Error<*> -> Result.Error(messageStrResult.error)
            is Result.Success -> {
                smsRepository.sendSms(phoneNo,messageStrResult.data)
                messagesRepository.upsertData(
                    MessagesDomain(logMessage, MessageTypeFromControlled.NORMAL),
                    email = email
                )
                Result.Success(Unit)
            }
        }
    }
}