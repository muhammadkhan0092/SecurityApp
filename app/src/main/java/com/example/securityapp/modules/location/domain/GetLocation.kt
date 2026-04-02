package com.example.securityapp.modules.location.domain

import android.util.Log
import com.example.securityapp.modules.messages.data.repository.AndroidMessageSerializer
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.location.data.FusedLocationRepository
import com.example.securityapp.modules.messages.domain.usecase.InsertMessage
import com.example.securityapp.modules.messages.domain.models.MessageFromControlled
import com.example.securityapp.modules.messages.domain.models.MessageTypeFromControlled
import javax.inject.Inject

class GetLocation @Inject constructor(
    private val locationRepository: FusedLocationRepository,
    private val androidSmsManagerRepository: AndroidMessageSerializer,
    private val insertMessage: InsertMessage
) {
    suspend operator fun invoke(number: String, email: String) {
        Log.d("KHAN","IN GET LOCATION")
        val location = locationRepository.getAccurateLocation()
        location?.let {
            Log.d("KHAN","IN LOCATION")
            val locationString = "https://www.google.com/maps/search/?api=1&query=${it.latitude},${it.longitude}"
            val messageFromControlled = MessageFromControlled(
                string = locationString,
                type = MessageTypeFromControlled.NORMAL
            )
            insertMessage(email, "Request : Location From $email\nStatus : Success" , MessageTypeFromControlled.NORMAL)
            val serializedMessage = androidSmsManagerRepository.serializeToString(messageFromControlled)
            when (serializedMessage) {
                is Result.Error<*> -> {
                    Log.d("KHAN","SERIALIZZED MESSAGE ERROR")
                    androidSmsManagerRepository.sendSms(number, serializedMessage.error)
                }
                is Result.Success -> {
                    Log.d("KHAN","SERIALIZED MESSAGE SUCCESS")
                    androidSmsManagerRepository.sendSms(number, serializedMessage.data)
                }
            }
        }
        Log.d("KHAN","AFTER LOCATION")
    }
}