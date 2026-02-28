package com.example.securityapp.modules.controlled.domain.usecase

import android.util.Log
import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.domain.MessageFromController
import com.example.securityapp.modules.controlled.data.repository.ControlledRepository
import javax.inject.Inject
import com.example.securityapp.utils.Result
import kotlin.text.contains

class HandleControlledMessageIntent @Inject constructor(
    private val controlledRepository : ControlledRepository,
    private val smsCommandRepository: SmsCommandRepository,
    private val blockApps: BlockApps,
    private val wipeGallery: WipeGallery,
    private val getLocation: GetLocation,
    private val factoryReset: FactoryReset
){
    suspend operator fun invoke(sender: String, message: String, email: String = "") {
        val controlledLocalResult = controlledRepository.getLocalData()
        when (controlledLocalResult) {
            is Result.Error ->{
                Log.d("KHAN","ERROR IN GETTING LOCATL DATA")
            }
            is Result.Success -> {
                val data = controlledLocalResult.data
                Log.d("KHAN","TOTAL NUMBERS ARE $data")
//                val filteredData = data.firstOrNull {
//                    sender in it.numbers
//                }
                val filteredData =listOf("+923218504409")
                when (filteredData) {
                    null -> {
                        Log.d("KHAN","FILTERED NUMBER IS NULL")
                        return
                    }
                    else -> {
                        Log.d("KHAN","FILTERED DATA IS $filteredData")
                        val result = smsCommandRepository.deserializeToMessageFromController(message)
                        when (result) {
                            is Result.Error<*> -> {
                                Log.d("KHAN","SERIALIZATION RESULT IS ${result.error}")
                            }
                            is Result.Success -> {
                                Log.d("KHAN","SERIALIZATION ERROR")
                                val messageFromController = result.data
                                when (messageFromController) {
                                    MessageFromController.BLOCK_APPS -> blockApps(filteredData,email)
                                    MessageFromController.WIPE_GALLERY -> wipeGallery(filteredData,email)
                                    MessageFromController.GET_LOCATION -> getLocation(filteredData,email)
                                    MessageFromController.FACTORY_RESET -> factoryReset(filteredData,email)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}