package com.example.securityapp.modules.controller.domain.usecase

import android.util.Log
import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.data.repository.RoomMessagesRepository
import com.example.securityapp.modules.controller.data.repository.ControllerRepository
import com.example.securityapp.modules.controller.domain.models.MessagesDomain
import com.example.securityapp.utils.Result
import javax.inject.Inject

class HandleControllerMessageIntent @Inject constructor(
    private val controllerRepository: ControllerRepository,
    private val smsCommandRepository: SmsCommandRepository,
    private val controllerMessageRepository: RoomMessagesRepository
) {
    suspend operator fun invoke(sender: String, message: String) {
        val controllerLocalResult = controllerRepository.getLocalData()
        when (controllerLocalResult) {
            is Result.Error -> Unit
            is Result.Success -> {
                val data = controllerLocalResult.data
                val filteredData = data.firstOrNull() {
                    sender in it.numbers
                }
                val deserializedMessageResult =
                    smsCommandRepository.deserializeToMessageFromControlled(message)
                when {
                    filteredData != null && deserializedMessageResult is Result.Success -> {
                        val deserializedMessage = deserializedMessageResult.data
                        val upsertResult = controllerMessageRepository.upsertData(
                            MessagesDomain(
                                message = deserializedMessage.string,
                                type = deserializedMessage.type
                            ),
                            email = filteredData.email
                        )
                        Log.d("KHAN", "UPSERT RESULT IS $upsertResult")
                    }

                    else -> Unit
                }
            }
        }
    }
}