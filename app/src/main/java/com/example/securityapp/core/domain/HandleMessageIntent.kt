package com.example.securityapp.core.domain

import android.util.Log
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.controlled.data.ControlledRepository
import com.example.securityapp.modules.controlled.data.FusedLocationRepository
import com.example.securityapp.modules.controller.data.repository.ControllerMessagesRepository
import com.example.securityapp.modules.controller.data.repository.ControllerRepository
import com.example.securityapp.modules.controller.domain.ControllerMessagesDomain
import com.example.securityapp.utils.Result
import javax.inject.Inject

class HandleMessageIntent @Inject constructor(
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation,
    private val controlledRepository: ControlledRepository,
    private val controllerRepository: ControllerRepository,
    private val smsCommandRepository: SmsCommandRepository,
    private val controllerMessagesRepository: ControllerMessagesRepository,
    private val locationRepository: FusedLocationRepository
) {
    suspend operator fun invoke(sender: String, message: String) {
        when (dataStoreRepositoryImplementation.getUserType()) {
            AppSettings.UserType.not_set -> Unit
            AppSettings.UserType.controller -> handleControllerMessageIntent(sender, message)
            AppSettings.UserType.controlled -> handleControlledMessageIntent(sender, message)
            AppSettings.UserType.UNRECOGNIZED -> Unit
        }
    }

    suspend fun handleControlledMessageIntent(sender: String, message: String) {
        val controlledLocalResult = controlledRepository.getLocalData()
        when (controlledLocalResult) {
            is Result.Error -> Unit
            is Result.Success -> {
                val data = controlledLocalResult.data
                val filteredData = data.firstOrNull {
                    sender in it.numbers
                }
                when (filteredData) {
                    null -> return
                    else -> {
                        val result =
                            smsCommandRepository.deserializeToMessageFromController(message)
                        when (result) {
                            is Result.Error<*> -> {
                            }

                            is Result.Success -> {
                                val messageFromController = result.data
                                when (messageFromController) {
                                    MessageFromController.BLOCK_APPS -> blockApps(filteredData.numbers)
                                    MessageFromController.WIPE_GALLERY -> wipeGallery(filteredData.numbers)
                                    MessageFromController.GET_LOCATION -> getLocation(filteredData.numbers)
                                    MessageFromController.FACTORY_RESET -> factoryReset(filteredData.numbers)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun blockApps(numbers : List<String>) {
        val firstNumber = numbers.firstOrNull()
        val messageFromControlled = MessageFromControlled(
            string = "Block Apps Complete",
            type = MessageTypeFromControlled.NORMAL
        )
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

    fun wipeGallery(numbers : List<String>) {
        val firstNumber = numbers.firstOrNull()
        val messageFromControlled = MessageFromControlled(
            string = "Factory Reset Complete",
            type = MessageTypeFromControlled.NORMAL
        )
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

    suspend fun getLocation(numbers: List<String>) {
        val location = locationRepository.getAccurateLocation()
        location?.let {
            val locationString =
                "https://www.google.com/maps/search/?api=1&query=${it.latitude},${it.longitude}"
            val firstNumber = numbers.firstOrNull()
            val messageFromControlled = MessageFromControlled(
                string = locationString,
                type = MessageTypeFromControlled.NORMAL
            )
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

    fun factoryReset(numbers: List<String>) {
        val firstNumber = numbers.firstOrNull()
        val messageFromControlled = MessageFromControlled(
            string = "Factory Reset Complete",
            type = MessageTypeFromControlled.NORMAL
        )
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

    suspend fun handleControllerMessageIntent(sender: String, message: String) {
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
                        val upsertResult = controllerMessagesRepository.upsertData(
                            ControllerMessagesDomain(
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