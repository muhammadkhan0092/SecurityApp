package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.repository.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.domain.InsertMessage
import com.example.securityapp.core.domain.MessageFromControlled
import com.example.securityapp.core.domain.MessageTypeFromControlled
import com.example.securityapp.modules.controlled.domain.repository.OverlayRepository
import com.example.securityapp.utils.Result
import javax.inject.Inject

class BlockApps @Inject constructor(
    private val smsCommandRepository: SmsCommandRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation,
    private val overlayRepository: OverlayRepository,
    private val insertMessage : InsertMessage
) {
    suspend operator fun invoke(numbers : List<String>,email : String = ""){
        val firstNumber = numbers.firstOrNull()
        val messageFromControlled = MessageFromControlled(
            string = "Block Apps Complete",
            type = MessageTypeFromControlled.NORMAL
        )
        overlayRepository.startOverlayService()
        val result = dataStoreRepositoryImplementation.setShouldBlock(true)
        when(result){
            true ->{
                insertMessage(email,"Blocking Apps From $email", MessageTypeFromControlled.NORMAL)
                val serializedMessage = smsCommandRepository.serializeToString(messageFromControlled)
                when (serializedMessage) {
                    is Result.Error<*> -> {
                        firstNumber?.let {
                            smsCommandRepository.sendSms(it, serializedMessage.error)
                        }
                    }
                    is Result.Success -> {
                        firstNumber?.let {
                            smsCommandRepository.sendSms(it, serializedMessage.data)
                        }
                    }
                }
            }
            false -> {
                insertMessage(email,"Error Blocking Apps From $email", MessageTypeFromControlled.ERROR)
                firstNumber?.let {
                    smsCommandRepository.sendSms(it, "Error Blocking Apps")
                }
            }
        }
    }
}