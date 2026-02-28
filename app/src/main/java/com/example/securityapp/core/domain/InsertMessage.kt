package com.example.securityapp.core.domain

import com.example.securityapp.core.data.repository.RoomMessagesRepository
import com.example.securityapp.modules.controller.domain.MessagesDomain
import javax.inject.Inject

class InsertMessage @Inject constructor(
    private val messagesRepository: RoomMessagesRepository
) {
    suspend operator fun invoke(email: String,message : String,type : MessageTypeFromControlled){
        messagesRepository.upsertData(
            data = MessagesDomain(
                message = message,
                type = type
            ),
            email = email
        )
    }
}