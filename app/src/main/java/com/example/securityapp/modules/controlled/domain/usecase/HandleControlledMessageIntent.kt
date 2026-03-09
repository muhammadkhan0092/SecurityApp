package com.example.securityapp.modules.controlled.domain.usecase

import android.util.Log
import com.example.securityapp.core.data.repository.AndroidSmsManagerRepository
import com.example.securityapp.core.domain.models.MessageFromController
import com.example.securityapp.modules.controlled.data.repository.FirebaseControlledRepository
import javax.inject.Inject
import com.example.securityapp.core.domain.utils.Result

class HandleControlledMessageIntent @Inject constructor(
    private val firebaseControlledRepository : FirebaseControlledRepository,
    private val androidSmsManagerRepository: AndroidSmsManagerRepository,
    private val blockApps: BlockApps,
    private val wipeGallery: WipeGallery,
    private val getLocation: GetLocation,
    private val factoryReset: FactoryReset
){
    suspend operator fun invoke(sender: String, message: String, email: String = "") {
        val controlledLocalResult = firebaseControlledRepository.getLocalData()
        when (controlledLocalResult) {
            is Result.Error ->{
                Log.d("KHAN","ERROR IN GETTING LOCATL DATA")
            }
            is Result.Success -> {
                val data = controlledLocalResult.data
                val filteredData = data.firstOrNull {
                    sender == it.number
                }?.number
                when (filteredData) {
                    null -> {
                        Log.d("KHAN","FILTERED NUMBER IS NULL")
                        return
                    }
                    else -> {
                        Log.d("KHAN","FILTERED DATA IS $filteredData")
                        val result = androidSmsManagerRepository.deserializeToMessageFromController(message)
                        when (result) {
                            is Result.Error<*> -> {
                                Log.d("KHAN","SERIALIZATION RESULT IS ${result.error}")
                            }
                            is Result.Success -> {
                                val messageFromController = result.data
                                when (messageFromController) {
                                    MessageFromController.BLOCK_APPS -> blockApps(
                                        filteredData,
                                        email
                                    )
                                    MessageFromController.WIPE_GALLERY -> wipeGallery(
                                        filteredData,
                                        email
                                    )
                                    MessageFromController.GET_LOCATION -> getLocation(
                                        filteredData,
                                        email
                                    )
                                    MessageFromController.FACTORY_RESET -> factoryReset(
                                        filteredData,
                                        email
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}