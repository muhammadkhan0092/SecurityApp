package com.example.securityapp.core.domain

import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.controlled.data.ControlledRepository
import com.example.securityapp.modules.controller.data.repository.ControllerRepository
import com.example.securityapp.modules.controller.data.models.MessageFromController
import com.example.securityapp.utils.Result
import javax.inject.Inject

class HandleMessageIntent @Inject constructor(
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation,
    private val controlledRepository: ControlledRepository,
    private val controllerRepository: ControllerRepository,
    private val smsCommandRepository: SmsCommandRepository
) {
    suspend operator fun invoke(sender : String, message : String){
        when(dataStoreRepositoryImplementation.getUserType()){
            AppSettings.UserType.not_set -> Unit
            AppSettings.UserType.controller -> handleControllerMessageIntent(sender,message)
            AppSettings.UserType.controlled-> handleControlledMessageIntent(sender,message)
            AppSettings.UserType.UNRECOGNIZED -> Unit
        }
    }
    suspend fun handleControllerMessageIntent(sender: String, message: String){
        val controllerLocalResult = controllerRepository.getLocalData()
        when(controllerLocalResult){
            is Result.Error -> Unit
            is Result.Success ->{
                val data = controllerLocalResult.data
                val filteredData = data.firstOrNull() {
                    sender in it.numbers
                }
                when(filteredData){
                    null-> return
                    else -> {
                        val result = smsCommandRepository.deserializeToMessageFromContainer(message)
                        when(result){
                            is Result.Error<*> -> {

                            }
                            is Result.Success-> {
                                val messageFromController = result.data
                                when(messageFromController){
                                    MessageFromController.BLOCK_APPS -> blockApps()
                                    MessageFromController.WIPE_GALLERY -> wipeGallery()
                                    MessageFromController.GET_LOCATION -> getLocation()
                                    MessageFromController.FACTORY_RESET -> factoryReset()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    fun blockApps(){

    }
    fun wipeGallery(){

    }
    fun getLocation(){

    }
    fun factoryReset(){

    }
    suspend fun handleControlledMessageIntent(sender : String,message : String){
        val controlledLocalResult = controlledRepository.getLocalData()
        when(controlledLocalResult) {
            is Result.Error -> Unit
            is Result.Success -> {
                val data = controlledLocalResult.data
                val filteredData = data.firstOrNull() {
                    sender in it.numbers
                }
                when(filteredData){
                    null-> Unit
                    else -> {

                    }
                }
            }
        }
    }
}