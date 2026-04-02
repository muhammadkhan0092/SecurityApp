package com.example.securityapp.modules.block_screen.domain

import com.example.securityapp.modules.app_settings.data.AppAppSettingsRepoImpl
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.messages.data.repository.AndroidMessageSerializer
import com.example.securityapp.modules.messages.domain.usecase.InsertMessage
import com.example.securityapp.modules.messages.domain.models.MessageFromControlled
import com.example.securityapp.modules.messages.domain.models.MessageTypeFromControlled
import com.example.securityapp.modules.overlay.domain.OverlayRepository
import javax.inject.Inject

class BlockApps @Inject constructor(
    private val androidSmsManagerRepository: AndroidMessageSerializer,
    private val appSettingsRepoImpl: AppAppSettingsRepoImpl,
    private val overlayRepository: OverlayRepository,
    private val insertMessage: InsertMessage
) {
    suspend operator fun invoke(firstNumber: String, email: String = "") {
        val messageFromControlled = MessageFromControlled(
            string = "Request : Block Screen\nStatus : Success",
            type = MessageTypeFromControlled.NORMAL
        )
        overlayRepository.startOverlayService()
        val result = appSettingsRepoImpl.setShouldBlock(true)
        when (result) {
            true -> {
                insertMessage(email, "Request : Block Screen From $email\nStatus : Success", MessageTypeFromControlled.NORMAL)
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
                    "Request : Block Screen From $email\nStatus : Failed",
                    MessageTypeFromControlled.ERROR
                )
                androidSmsManagerRepository.sendSms(firstNumber, "Error Blocking Apps")
            }
        }
    }
}