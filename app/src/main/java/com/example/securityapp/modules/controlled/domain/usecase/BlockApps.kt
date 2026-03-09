package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.core.data.repository.AndroidSmsManagerRepository
import com.example.securityapp.core.domain.usecase.InsertMessage
import com.example.securityapp.core.domain.models.MessageFromControlled
import com.example.securityapp.core.domain.models.MessageTypeFromControlled
import com.example.securityapp.modules.controlled.domain.repository.OverlayRepository
import com.example.securityapp.core.domain.utils.Result
import javax.inject.Inject

class BlockApps @Inject constructor(
    private val androidSmsManagerRepository: AndroidSmsManagerRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val overlayRepository: OverlayRepository,
    private val insertMessage: InsertMessage
) {
    suspend operator fun invoke(firstNumber: String, email: String = "") {
        val messageFromControlled = MessageFromControlled(
            string = "Block Apps Complete",
            type = MessageTypeFromControlled.NORMAL
        )
        overlayRepository.startOverlayService()
        val result = dataStoreRepository.setShouldBlock(true)
        when (result) {
            true -> {
                insertMessage(email, "Blocking Apps From $email", MessageTypeFromControlled.NORMAL)
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

            false -> {
                insertMessage(
                    email,
                    "Error Blocking Apps From $email",
                    MessageTypeFromControlled.ERROR
                )
                androidSmsManagerRepository.sendSms(firstNumber, "Error Blocking Apps")
            }
        }
    }
}