package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.domain.InsertMessage
import com.example.securityapp.core.domain.MessageFromControlled
import com.example.securityapp.core.domain.MessageTypeFromControlled
import com.example.securityapp.modules.controlled.data.repository.FusedLocationRepository
import javax.inject.Inject
import com.example.securityapp.utils.Result
class GetLocation @Inject constructor(
    private val locationRepository: FusedLocationRepository,
    private val smsCommandRepository: SmsCommandRepository,
    private val insertMessage: InsertMessage
){
    suspend operator fun invoke(numbers: List<String>, email: String) {
        val location = locationRepository.getAccurateLocation()
        location?.let {
            val locationString = "https://www.google.com/maps/search/?api=1&query=${it.latitude},${it.longitude}"
            val firstNumber = numbers.firstOrNull()
            val messageFromControlled = MessageFromControlled(
                string = locationString,
                type = MessageTypeFromControlled.NORMAL
            )
            insertMessage(email,"Location Sent to $email", MessageTypeFromControlled.NORMAL)
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
    }
}