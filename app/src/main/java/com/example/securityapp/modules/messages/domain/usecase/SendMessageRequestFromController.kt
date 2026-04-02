package com.example.securityapp.modules.messages.domain.usecase

import com.example.securityapp.modules.messages.data.repository.RoomMessagesRepository
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.messages.domain.models.MessagesDomain
import com.example.securityapp.modules.messages.data.repository.AndroidMessageSerializer
import com.example.securityapp.modules.messages.domain.models.MessageFromController
import com.example.securityapp.modules.messages.domain.models.MessageTypeFromControlled
import javax.inject.Inject

class SendMessageRequestFromController @Inject constructor(
    private val smsRepository: AndroidMessageSerializer,
    private val messagesRepository: RoomMessagesRepository
) {
    suspend operator fun invoke(phoneNo : String, data : MessageFromController, email : String) : Result<Unit> {
        val logMessage = when(data){
            MessageFromController.BLOCK_APPS -> "Block Screen Requested"
            MessageFromController.WIPE_GALLERY -> "Wipe Gallery Requested"
            MessageFromController.GET_LOCATION -> "Get Location Requested"
            MessageFromController.FACTORY_RESET -> "Factory Reset Requested"
            MessageFromController.UNINSTALL_APPS -> "Uninstall Apps Requested"
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