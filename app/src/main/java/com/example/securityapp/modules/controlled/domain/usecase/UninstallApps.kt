package com.example.securityapp.modules.controlled.domain.usecase

import android.util.Log
import com.example.securityapp.core.data.repository.AndroidSmsManagerRepository
import com.example.securityapp.modules.messages.MessageFromControlled
import com.example.securityapp.modules.messages.MessageTypeFromControlled
import com.example.securityapp.modules.uninstall.UninstallRepository
import com.example.securityapp.modules.messages.InsertMessage
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controlled.domain.repository.DeviceOwnerRepository
import javax.inject.Inject

class UninstallApps @Inject constructor(
    private val androidSmsManagerRepository: AndroidSmsManagerRepository,
    private val uninstallRepository: UninstallRepository,
    private val insertMessage : InsertMessage,
    private val deviceOwnerRepository: DeviceOwnerRepository
) {
    suspend operator fun invoke(number: String, email: String = "") {
        Log.d("KHAN","IN UNINSTALL APPS")
        val isDeviceOwner = deviceOwnerRepository.isDeviceOwner()
        Log.d("KHAN","AFTER DEVICE OWNER $isDeviceOwner")
        if(!isDeviceOwner){
            insertMessage(email,"Not Device Owner, Requested From $email", type = MessageTypeFromControlled.ERROR)
            sendMessageToOtherDevice("Not Device Owner", MessageTypeFromControlled.ERROR,number)
            return
        }
        val result = uninstallRepository.getData()
        when(result){
            is Result.Error<*> -> {
                insertMessage(email,"Uninstall Db Error, Requested From $email", type = MessageTypeFromControlled.ERROR)
                sendMessageToOtherDevice("Uninstall Db Error", MessageTypeFromControlled.ERROR,number)
            }
            is Result.Success->{
                when(result.data.isEmpty()){
                    true ->  {
                        insertMessage(email,"No Apps To Uninstall, Requested From $email", type = MessageTypeFromControlled.ERROR)
                        sendMessageToOtherDevice("NO APPS TO UNINSTALL", MessageTypeFromControlled.ERROR,number)
                    }
                    false -> {
                        result.data.forEach {
                            uninstallRepository.uninstallApp(it.packageName)
                        }
                        insertMessage(email,"${result.data.size} Apps Deleted, Requested From $email", type = MessageTypeFromControlled.NORMAL)
                        sendMessageToOtherDevice("${result.data.size} Apps Deleted", MessageTypeFromControlled.NORMAL,number)
                    }
                }
            }
        }
    }
    fun sendMessageToOtherDevice(message : String,type : MessageTypeFromControlled,number : String){
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