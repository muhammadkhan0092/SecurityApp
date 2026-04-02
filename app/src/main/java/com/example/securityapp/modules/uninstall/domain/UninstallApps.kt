package com.example.securityapp.modules.uninstall.domain

import android.util.Log
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.device_owner.domain.DeviceOwnerRepository
import com.example.securityapp.modules.messages.data.repository.AndroidMessageSerializer
import com.example.securityapp.modules.messages.domain.usecase.InsertMessage
import com.example.securityapp.modules.messages.domain.models.MessageFromControlled
import com.example.securityapp.modules.messages.domain.models.MessageTypeFromControlled
import javax.inject.Inject

class UninstallApps @Inject constructor(
    private val androidSmsManagerRepository: AndroidMessageSerializer,
    private val uninstallRepository: UninstallRepository,
    private val insertMessage : InsertMessage,
    private val deviceOwnerRepository: DeviceOwnerRepository
) {
    suspend operator fun invoke(number: String, email: String = "") {
        Log.d("KHAN","IN UNINSTALL APPS")
        val isDeviceOwner = deviceOwnerRepository.isDeviceOwner()
        Log.d("KHAN","AFTER DEVICE OWNER $isDeviceOwner")
        if(!isDeviceOwner){
            insertMessage(email,"Request : Uninstall Apps From $email\nStatus : Failed\nReason : App not Device Owner", type = MessageTypeFromControlled.ERROR)
            sendMessageToOtherDevice("Request : Uninstall Apps\nStatus : Failed\nReason : App not Device Owner", MessageTypeFromControlled.ERROR,number)
            return
        }
        val result = uninstallRepository.getData()
        when(result){
            is Result.Error<*> -> {
                insertMessage(email,"Request : Uninstall Apps From $email\nStatus : Failed\nReason : Db Error", type = MessageTypeFromControlled.ERROR)
                sendMessageToOtherDevice("Request : Uninstall Apps\nStatus : Failed\nReason : Db Error", MessageTypeFromControlled.ERROR,number)
            }
            is Result.Success->{
                when(result.data.isEmpty()){
                    true ->  {
                        insertMessage(email,"Request : Uninstall Apps From $email\nStatus : Failed\nReason : No Apps to Uninstall", type = MessageTypeFromControlled.ERROR)
                        sendMessageToOtherDevice("Request : Uninstall Apps\nStatus : Failed\nReason : No Apps to Uninstall", MessageTypeFromControlled.ERROR,number)
                    }
                    false -> {
                        result.data.forEach {
                            uninstallRepository.uninstallApp(it.packageName)
                        }
                        insertMessage(email,"Request : Uninstall Apps From $email\nStatus : ${result.data.size} Apps Deleted", type = MessageTypeFromControlled.NORMAL)
                        sendMessageToOtherDevice("Request : Uninstall Apps\nStatus: ${result.data.size} Apps Deleted", MessageTypeFromControlled.NORMAL,number)
                    }
                }
            }
        }
    }
    fun sendMessageToOtherDevice(message : String, type : MessageTypeFromControlled, number : String){
        val messageFromControlled = MessageFromControlled(
            string = message,
            type = type
        )
        val serializedMessage = androidSmsManagerRepository.serializeToString(messageFromControlled)
        when (serializedMessage) {
            is Result.Error<*> -> {
                androidSmsManagerRepository.sendSms(number, serializedMessage.error)
            }

            is Result.Success -> {
                androidSmsManagerRepository.sendSms(number, serializedMessage.data)
            }
        }
    }

}