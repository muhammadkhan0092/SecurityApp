package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.modules.messages.InsertMessage
import com.example.securityapp.modules.messages.MessageFromControlled
import com.example.securityapp.modules.messages.MessageTypeFromControlled
import com.example.securityapp.modules.controlled.data.repository.AndroidGalleryRepository
import com.example.securityapp.modules.permissions.PermissionManager
import javax.inject.Inject

class WipeGallery @Inject constructor(
    private val insertMessage: InsertMessage,
    private val permissionManager: PermissionManager,
    private val androidGalleryRepository: AndroidGalleryRepository,
    private val sendMessageToController: SendMessageToController
) {
    suspend operator fun invoke(
        numbers: String,
        email: String
    ){
        if(!permissionManager.hasManageAllFilesPermission()){
            val messageFromControlled = MessageFromControlled(
                string = "Request : Gallery Wipe\nStatus : Failed\nReason : Permission Error",
                type = MessageTypeFromControlled.ERROR
            )
            insertMessage(email,"Request : Gallery Wipe From $email\nStatus : Failed\nReason : Permission Error", MessageTypeFromControlled.NORMAL)
            sendMessageToController(numbers,messageFromControlled)
        }
        else{
            androidGalleryRepository.deleteAllGalleryFiles()
            val messageFromControlled = MessageFromControlled(
                string = "Request : Gallery Wipe\nStatus : Success",
                type = MessageTypeFromControlled.NORMAL
            )
            insertMessage(email,"Request : Gallery Wipe From $email\nStatus : Success", MessageTypeFromControlled.NORMAL)
            sendMessageToController(numbers,messageFromControlled)
        }
    }
}