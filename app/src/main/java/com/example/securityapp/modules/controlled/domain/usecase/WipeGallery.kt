package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.domain.usecase.InsertMessage
import com.example.securityapp.core.domain.models.MessageFromControlled
import com.example.securityapp.core.domain.models.MessageTypeFromControlled
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
                string = "Gallery Wipe Permission Error",
                type = MessageTypeFromControlled.ERROR
            )
            insertMessage(email,"Gallery Wipe From $email Permission Error", MessageTypeFromControlled.NORMAL)
            sendMessageToController(numbers,messageFromControlled)
        }
        else{
            androidGalleryRepository.deleteAllGalleryFiles()
            val messageFromControlled = MessageFromControlled(
                string = "Gallery Wipe Complete",
                type = MessageTypeFromControlled.NORMAL
            )
            insertMessage(email,"Gallery Wipe From $email", MessageTypeFromControlled.NORMAL)
            sendMessageToController(numbers,messageFromControlled)
        }
    }
}