package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.domain.MessageFromControlled
import com.example.securityapp.core.domain.MessageTypeFromControlled
import com.example.securityapp.utils.Result
import javax.inject.Inject

class BlockApps @Inject constructor(
    private val smsCommandRepository: SmsCommandRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation
) {
    suspend operator fun invoke(numbers : List<String>){
        val firstNumber = numbers.firstOrNull()
        val messageFromControlled = MessageFromControlled(
            string = "Block Apps Complete",
            type = MessageTypeFromControlled.NORMAL
        )
        val result = dataStoreRepositoryImplementation.setShouldBlock(true)
        when(result){
            true ->{
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
                firstNumber?.let {
                    smsCommandRepository.sendSms(it, "Error Blocking Apps")
                }
            }
        }
    }
}