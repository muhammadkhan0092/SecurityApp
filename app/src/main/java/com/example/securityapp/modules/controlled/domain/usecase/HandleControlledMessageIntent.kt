package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.domain.MessageFromController
import com.example.securityapp.modules.controlled.data.ControlledRepository
import javax.inject.Inject
import com.example.securityapp.utils.Result
class HandleControlledMessageIntent @Inject constructor(
    private val controlledRepository : ControlledRepository,
    private val smsCommandRepository: SmsCommandRepository,
    private val blockApps: BlockApps,
    private val wipeGallery: WipeGallery,
    private val getLocation: GetLocation,
    private val factoryReset: FactoryReset
){
    suspend operator fun invoke(sender: String, message: String) {
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
                        val result = smsCommandRepository.deserializeToMessageFromController(message)
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
}