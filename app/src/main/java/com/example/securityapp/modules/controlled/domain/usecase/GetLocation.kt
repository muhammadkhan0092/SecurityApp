package com.example.securityapp.modules.controlled.domain.usecase

import android.util.Log
import com.example.securityapp.core.data.repository.AndroidSmsManagerRepository
import com.example.securityapp.modules.messages.InsertMessage
import com.example.securityapp.modules.messages.MessageFromControlled
import com.example.securityapp.modules.messages.MessageTypeFromControlled
import com.example.securityapp.modules.controlled.data.repository.FusedLocationRepository
import javax.inject.Inject
import com.example.securityapp.core.domain.utils.Result

class GetLocation @Inject constructor(
    private val locationRepository: FusedLocationRepository,
    private val androidSmsManagerRepository: AndroidSmsManagerRepository,
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
            insertMessage(email, "Location Sent to $email", MessageTypeFromControlled.NORMAL)
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