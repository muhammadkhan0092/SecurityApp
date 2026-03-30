package com.example.securityapp.modules.controller.domain.usecase

import android.util.Log
import com.example.securityapp.modules.messages.data.AndroidSmsManagerRepository
import com.example.securityapp.core.data.repository.RoomMessagesRepository
import com.example.securityapp.modules.controller.data.repository.FirebaseControllerRepository
import com.example.securityapp.modules.controller.domain.models.MessagesDomain
import com.example.securityapp.core.domain.utils.Result
import javax.inject.Inject

class HandleControllerMessageIntent @Inject constructor(
    private val firebaseControllerRepository: FirebaseControllerRepository,
    private val androidSmsManagerRepository: AndroidSmsManagerRepository,
    private val controllerMessageRepository: RoomMessagesRepository
) {
    suspend operator fun invoke(sender: String, message: String) {
        val controllerLocalResult = firebaseControllerRepository.getLocalData()
        when (controllerLocalResult) {
            is Result.Error -> Unit
            is Result.Success -> {
                val data = controllerLocalResult.data
                val filteredData = data.firstOrNull() {
                    sender == it.number
                }
                val deserializedMessageResult = androidSmsManagerRepository.deserializeToMessageFromControlled(message)
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